module org.example {
    requires javafx.controls;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    exports example;
    exports example.model;
    exports example.rest;
}
