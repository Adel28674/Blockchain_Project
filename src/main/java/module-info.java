module com.example.blockchain {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.jfree.jfreechart;
    requires java.desktop;


    opens com.example.blockchain to javafx.fxml;
    exports com.example.blockchain;
    exports com.example.blockchain.modele;
    opens com.example.blockchain.modele to javafx.fxml;

}