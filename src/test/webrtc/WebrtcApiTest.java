import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.RestAssuredUtil;
import util.TestUtil;

import java.util.LinkedHashMap;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class WebrtcApiTest{

    public Response res = null;
    public JsonPath jp  = null;

    TestUtil testUtil = new TestUtil();

    @BeforeClass
    public void setup() {

        RestAssuredUtil.setBaseURI();
        RestAssuredUtil.setBasePath("webrtc");
        RestAssuredUtil.setContentType(ContentType.JSON);
    }

    @AfterClass
    public void afterTest() {
        //Reset Values
        RestAssuredUtil.resetBaseURI();
        RestAssuredUtil.resetBasePath();
    }

    @Test
    public void T01_WebRtcGetAllDevicesTest() {
        res = given().get("/devices");
        Assert.assertEquals(res.getStatusCode(), 200, "Status Check Failed!");
        jp = RestAssuredUtil.getJsonPath(res);
        System.out.println(testUtil.getClients(jp));
    }

    @Test
    public void T02_WebRtcGetAllDeviceByIdTest() {
        res = given()
                .contentType(ContentType.JSON)
                .pathParams("deviceid", "someramdomIvalidId")
                .get("/device/{deviceid}");
        Assert.assertEquals(res.getStatusCode(), 404, "Status Check Failed!");
        jp = RestAssuredUtil.getJsonPath(res);
        System.out.println(testUtil.getErrorResponse(jp));
        Assert.assertEquals(Optional.ofNullable((Integer) jp.get("statusCode")), Optional.of(404));
        Assert.assertEquals(jp.get("message"), "No device found with the given device id");
        Assert.assertEquals(jp.get("details"), "uri=/webrtc/device/someramdomIvalidId");
    }

    @Test
    public void T02_WebRtcGetDeviceAuditInfoTest() {
        res = given()
                .contentType(ContentType.JSON)
                .pathParams("deviceid", "someramdomIvalidId")
                .get("/audit/device/{deviceid}");
        Assert.assertEquals(res.getStatusCode(), 404, "Status Check Failed!");
        jp = RestAssuredUtil.getJsonPath(res);
        System.out.println(testUtil.getErrorResponse(jp));
        Assert.assertEquals(Optional.ofNullable((Integer) jp.get("statusCode")), Optional.of(404));
        Assert.assertEquals(jp.get("message"), "No device found with the given device id");
        Assert.assertEquals(jp.get("details"), "uri=/webrtc/audit/device/someramdomIvalidId");
    }

    @Test
    public void T03_WebRtcGetAddDeviceTest() {
        res = given()
                .contentType(ContentType.JSON)
                .body("{}")
                .post("/device");
        Assert.assertEquals(res.getStatusCode(), 400, "Status Check Failed!");
        jp = RestAssuredUtil.getJsonPath(res);
        System.out.println(testUtil.getErrorResponse(jp));
        LinkedHashMap<String, String> validationErrors = jp.get("validationErrors");
        Assert.assertEquals(validationErrors.get("deviceDetails"), "Device details are mandatory.");
        Assert.assertEquals(validationErrors.get("deviceId"), "Device Id is mandatory.");
        Assert.assertEquals(jp.get("details"), "uri=/webrtc/device");
    }
}
