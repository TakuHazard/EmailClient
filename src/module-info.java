module JavaFxEmailClientCourse {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;
    opens com.taku.view;
    opens com.taku.controller;
    opens com.taku;
    opens com.taku.model;
}