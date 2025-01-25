module com.ouroboros.sleepingqueen {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires com.fasterxml.jackson.databind;
    requires json.simple;
    requires junit;

    opens com.ouroboros.sleepingqueen to javafx.fxml;
    opens com.ouroboros.sleepingqueen.card to javafx.fxml;

    exports com.ouroboros.sleepingqueen;
    exports com.ouroboros.sleepingqueen.card;
    exports com.ouroboros.sleepingqueen.testScreen;
    opens com.ouroboros.sleepingqueen.testScreen to javafx.fxml;
    exports com.ouroboros.sleepingqueen.subPlayer;
    opens com.ouroboros.sleepingqueen.subPlayer to javafx.fxml;
    exports com.ouroboros.sleepingqueen.mainPlayer;
    opens com.ouroboros.sleepingqueen.mainPlayer to javafx.fxml;
    exports com.ouroboros.sleepingqueen.controller;
    opens com.ouroboros.sleepingqueen.controller to javafx.fxml;
}