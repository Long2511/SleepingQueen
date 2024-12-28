module com.ouroboros.sleepingqueen {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires com.fasterxml.jackson.databind;
    requires json.simple;

    opens com.ouroboros.sleepingqueen to javafx.fxml;
    opens com.ouroboros.sleepingqueen.card to javafx.fxml;

    exports com.ouroboros.sleepingqueen;
    exports com.ouroboros.sleepingqueen.card;
    exports com.ouroboros.sleepingqueen.testScreen;
    opens com.ouroboros.sleepingqueen.testScreen to javafx.fxml;
}