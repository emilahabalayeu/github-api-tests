package com.solvd.carina.demo.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}/user/repos", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/github/repos/_post/rq.json")
@ResponseTemplatePath(path = "api/github/repos/_post/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.CREATED_201)
public class PostGithubRepoMethod extends AbstractApiMethodV2 {

    public PostGithubRepoMethod() {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
        setHeader("Authorization", "Bearer " + Configuration.getRequired("github_token"));
        setHeader("Content-Type", "application/json");
    }
}
