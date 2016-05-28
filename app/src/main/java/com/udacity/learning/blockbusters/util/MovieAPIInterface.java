package com.udacity.learning.blockbusters.util;

import com.udacity.learning.blockbusters.model.MoviesContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Subbu on 5/28/16.
 */
public interface MovieAPIInterface {
    @GET("popular")
    Call<MoviesContainer> fetchMoviesList(@Query("api_key") String key);
}

/*public interface ChaturaAPIService {

    @POST("/users/registerUser")
    Call<UserRegRequestInfo> registerNewUser(@Body UserRegRequestInfo userRegRequestInfo);

    @POST("/users/authUser")
    Call<UserAuthRequest> authenticateUser(@Body UserAuthRequest authRequest);

    @POST("/user/{userID}/addNewLight")
    Call<ChaturaResponse> addNewLight(@Path("userID") String userID, @Body Light newLight);

    @GET("/user/{userID}/fetchUserDashboardDetails")
    Call<UserInfo> fetchUserDashboardDetails(@Path("userID") String userID);
}*/
