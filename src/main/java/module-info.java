module Game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

            requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
            
    opens Game to javafx.fxml;
    exports Game;
}