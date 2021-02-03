package com.ahmettekin.WeatherApp.service;

import com.ahmettekin.WeatherApp.model.CityModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CityAPI {

    /*@Headers({
            "Access-Control-Allow-Origin: *",
            "Cache-Control: no-cacheprivate",
            "Cf-Cache-Status: DYNAMIC",
            "Cf-Ray: 61a61afd4c423752-MXP",
            "Cf-Request-Id: 07fbaf324a00003752240a9000000001",
            "Content-Type: application/json",
            "Date: Sun31 Jan 2021 20:15:34 GMT",
            "Expect-Ct: max-age=604800report-uri=\"https://report-uri.cloudflare.com/cdn-cgi/beacon/expect-ct",
            "Nel: {\"max_age\":604800,\"report_to\":\"cf-nel\"}",
            "Report-To: {\"group\":\"cf-nel\",\"max_age\":604800,\"endpoints\":[{\"url\":\"https:\\/\\/a.nel.cloudflare.com\\/report?s=LL%2FbzscqIPwJfkt%2Ff2DmCMTr6pGTxTKdTAzLkOB87OCE1iNckvmQAXvOKJi77u5h6gw%2BKobucBrhXL0juFEYaVG0jjgK9sRV0dO5sxnKNXzKUejaIIbBxw%3D%3D\"}]}",
            "Server: cloudflare",
            "X-CSCAPI-KEY: c0ZGS2VpUlJkNHU0VFlBeWplWEcyVEVmMEJjNzZYcm1jVG5YdGdpeQ=="
    })*/
    @GET("ahmettekin1063/CityListJson/master/city.list.tr.json")
    Call<List<CityModel>> getData();

}
