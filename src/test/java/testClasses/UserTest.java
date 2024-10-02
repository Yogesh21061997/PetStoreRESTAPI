package testClasses;

import com.github.javafaker.Faker;
import endPoints.UserEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payLoad.User;
//@Listeners({Utilility.ExtentReportManager.class})
public class UserTest  {

    User userdata;
    Faker faker;
    @BeforeClass
    public void loadData(){
        faker = new Faker();
        userdata = new User();
        userdata.setId(faker.number().hashCode());
        userdata.setUsername(faker.name().username());
        userdata.setFirstName(faker.name().firstName());
        userdata.setLastName(faker.name().lastName());
        userdata.setEmail(faker.internet().emailAddress());
        userdata.setPassword(faker.internet().password());
        userdata.setPhone(faker.phoneNumber().phoneNumber());
        userdata.setUserStatus(0);
    }

    @Test(priority = 1)
    public void postUser(){
       Response response = UserEndPoints.postUser(userdata);
       response.then().log().all();
       Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority = 2)
    public void getUser(){
        Response response = UserEndPoints.getUser(this.userdata.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority = 3)
    public void updateUser(){
        userdata.setFirstName(faker.name().firstName());
        userdata.setLastName(faker.name().lastName());
        userdata.setEmail(faker.internet().emailAddress());
        Response response = UserEndPoints.updateUser(this.userdata.getUsername(),userdata);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);

        //Validation after update
        Response responseAfterUpdate = UserEndPoints.getUser(this.userdata.getUsername());
        responseAfterUpdate.then().log().all();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
    }

    @Test(priority = 4,enabled = true)
    public void deleteUser(){
        Response response = UserEndPoints.deleteUser(this.userdata.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);

        //Validation after deletion
        Response responseAfterUpdate = UserEndPoints.getUser(this.userdata.getUsername());
        responseAfterUpdate.then().log().all();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(),404);
    }
}
