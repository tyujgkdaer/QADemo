package com.qatest.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = {"src/main/resources/login.feature"
        ,"src/main/resources/shopping.feature"
},
glue = "com.qatest.steps", monochrome = true)

public class SwaglabsRunner extends AbstractTestNGCucumberTests {
}
