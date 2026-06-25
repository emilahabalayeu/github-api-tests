package com.solvd.carina.demo;

import com.solvd.carina.demo.api.*;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.annotations.Test;

public class GithubApiTest implements IAbstractTest {

    private static final String USERNAME = "emilahabalayeu";
    private static final String REPO_NAME = "test-repo-carina";
    private static final String REPO_DESCRIPTION = "Test repo created by Carina";

    // 1. Позитивный тест - получить существующего пользователя
    @Test
    public void testGetExistingUser() {
        GetGithubUserMethod api = new GetGithubUserMethod(USERNAME);
        api.callAPIExpectSuccess();
    }

    // 2. Негативный тест - получить несуществующего пользователя
    @Test
    public void testGetNonExistingUser() {
        GetGithubUserMethod api = new GetGithubUserMethod("thisuserdoesnotexist123456789abc");
        api.expectResponseStatus(com.zebrunner.carina.api.http.HttpResponseStatusType.NOT_FOUND_404);
        api.callAPI();
    }

    // 3. Позитивный тест - получить существующий репозиторий
    @Test
    public void testGetExistingRepo() {
        GetGithubRepoMethod api = new GetGithubRepoMethod("octocat", "Hello-World");
        api.callAPIExpectSuccess();
    }

    // 4. Негативный тест - получить несуществующий репозиторий
    @Test
    public void testGetNonExistingRepo() {
        GetGithubRepoMethod api = new GetGithubRepoMethod(USERNAME, "this-repo-does-not-exist-abc123");
        api.expectResponseStatus(com.zebrunner.carina.api.http.HttpResponseStatusType.NOT_FOUND_404);
        api.callAPI();
    }

    // 5. Позитивный тест - получить список репозиториев пользователя
    @Test
    public void testGetUserRepos() {
        GetGithubUserReposMethod api = new GetGithubUserReposMethod(USERNAME);
        api.callAPIExpectSuccess();
    }

    // 6. Негативный тест - список репозиториев несуществующего пользователя
    @Test
    public void testGetUserReposNonExistingUser() {
        GetGithubUserReposMethod api = new GetGithubUserReposMethod("thisuserdoesnotexist123456789abc");
        api.expectResponseStatus(com.zebrunner.carina.api.http.HttpResponseStatusType.NOT_FOUND_404);
        api.callAPI();
    }

    // 7. Позитивный тест - создать репозиторий
    @Test
    public void testCreateRepo() {
        PostGithubRepoMethod api = new PostGithubRepoMethod();
        api.addProperty("repo_name", REPO_NAME);
        api.addProperty("repo_description", REPO_DESCRIPTION);
        api.callAPIExpectSuccess();
    }

    // 8. Негативный тест - создать репозиторий с уже существующим именем
    @Test(dependsOnMethods = "testCreateRepo")
    public void testCreateDuplicateRepo() {
        PostGithubRepoMethod api = new PostGithubRepoMethod();
        api.addProperty("repo_name", REPO_NAME);
        api.addProperty("repo_description", REPO_DESCRIPTION);
        api.expectResponseStatus(com.zebrunner.carina.api.http.HttpResponseStatusType.UNPROCESSABLE_ENTITY_422);
        api.callAPI();
    }

    // 9. Позитивный тест - удалить репозиторий
    @Test(dependsOnMethods = "testCreateRepo")
    public void testDeleteRepo() {
        DeleteGithubRepoMethod api = new DeleteGithubRepoMethod(USERNAME, REPO_NAME);
        api.callAPIExpectSuccess();
    }

    // 10. Негативный тест - удалить несуществующий репозиторий
    @Test
    public void testDeleteNonExistingRepo() {
        DeleteGithubRepoMethod api = new DeleteGithubRepoMethod(USERNAME, "this-repo-does-not-exist-abc123");
        api.expectResponseStatus(com.zebrunner.carina.api.http.HttpResponseStatusType.NOT_FOUND_404);
        api.callAPI();
    }
}
