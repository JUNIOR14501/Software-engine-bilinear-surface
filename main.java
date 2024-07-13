package com.example;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;
/**
 * JavaFX App
 */
public class App extends Application {
    double angRotateX = 0;
    double angRotateY = 0;
    //Coords input <- Menu
    Text textP00 = new Text("P00");
    TextField P00X = new TextField("0,0");
    TextField P00Y = new TextField("0,0");
    TextField P00Z = new TextField("1,0");
    VBox colTextField1 = new VBox(textP00, P00X, P00Y, P00Z);

    Text textP01 = new Text("P01");
    TextField P01X = new TextField("1,0");
    TextField P01Y = new TextField("1,0");
    TextField P01Z = new TextField("1,0");
    VBox colTextField2 = new VBox(textP01, P01X, P01Y, P01Z);

    Text textP10 = new Text("P10");
    TextField P10X = new TextField("1,0");
    TextField P10Y = new TextField("0,0");
    TextField P10Z = new TextField("0,0");
    VBox colTextField3 = new VBox(textP10, P10X, P10Y, P10Z);

    Text textP11 = new Text("P11");
    TextField P11X = new TextField("0,0");
    TextField P11Y = new TextField("1,0");
    TextField P11Z = new TextField("0,0");
    VBox colTextField4 = new VBox(textP11, P11X, P11Y, P11Z);
    SmartGroup nodesGroupe = new SmartGroup();
    @Override
    public void start(Stage stage) throws IOException {
        //3D models
        //XY
        Line lineX = new Line(-600, 0, 600, 0);
        lineX.setStrokeWidth(5);
        Line lineY = new Line(0, -600, 0, 600);
        lineY.setStrokeWidth(5);

        nodesGroupe.getChildren().add(lineX);
        nodesGroupe.getChildren().add(lineY);

        colTextField1.setAlignment(Pos.CENTER);
        colTextField1.setSpacing(5);
        colTextField1.setMaxWidth(40);
        colTextField2.setAlignment(Pos.CENTER);
        colTextField2.setSpacing(5);
        colTextField2.setMaxWidth(40);
        colTextField3.setAlignment(Pos.CENTER);
        colTextField3.setSpacing(5);
        colTextField3.setMaxWidth(40);
        colTextField4.setAlignment(Pos.CENTER);
        colTextField4.setSpacing(5);
        colTextField4.setMaxWidth(40);
        Text textSpace = new Text(" ");
        Text textX = new Text("X");
        Text textY = new Text("Y");
        Text textZ = new Text("Z");

        VBox colTextField5 = new VBox(12, textSpace, textX, textY, textZ);
        colTextField5.setMaxWidth(40);

        HBox inputCoord = new HBox(5, colTextField1, colTextField2, colTextField3, colTextField4, colTextField5);
        //Sliders <- Menu
        Text rotateXText = new Text("Rotate X");
        Text rotateYText = new Text("Rotate Y");
        Slider rotateX = new Slider();
        Slider rotateY = new Slider();
        rotateX.setMinWidth(6);
        rotateX.setMax(6);
        rotateY.setMax(6);
        rotateX.setMin(-6);
        rotateY.setMin(-6);
        rotateX.setValue(0);
        rotateY.setValue(0);
        rotateX.setShowTickMarks(true);
        rotateY.setShowTickMarks(true);
        rotateX.setShowTickLabels(true);
        rotateY.setShowTickLabels(true);
        rotateX.valueProperty().addListener(new ChangeListener < Number > () {
                public void changed(ObservableValue << ? extends Number > obsVal,
                    Number oldVal, Number newVal) {

                    angRotateX = newVal.intValue();
                    double resetAng = 0;
                    if (angRotateX > resetAng) {
                        angRotateX = angRotateX - resetAng;
                        resetAng = angRotateX;



                    }
                });

        }
        nodesGroupe.rotateByX(angRotateX);
        System.out.println(angRotateX);

        rotateY.valueProperty().addListener(new ChangeListener < Number > () {
            public void changed(ObservableValue << ? extends Number > obsVal,
                Number oldVal, Number newVal) {

                angRotateY = newVal.intValue();
                nodesGroupe.rotateByY(angRotateY);
                System.out.println(angRotateY);
            }
        });
        //График build3D();
        VBox sliders = new VBox(rotateXText, rotateX, rotateYText, rotateY);
        Button update = new Button("Update");
        Button reset = new Button("Reset");
        reset.setOnAction(new ResetButton());
        update.setOnAction(new UpdateButton());
        VBox buttons = new VBox(update, reset);
        //Картинка с примером
        Image img = new Image(new FileInputStream("demo/src/main/resources/img/ex.jpg"));
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(370);
        imageView.setFitHeight(210);
        HBox menu1 = new HBox(inputCoord, sliders, buttons);
        VBox menu = new VBox(menu1, imageView);
        menu.setTranslateX(-800);
        menu.setTranslateY(-400);
        menu.setTranslateZ(-450);
        menu.setSpacing(20);
        //Cam
        Camera cam = new PerspectiveCamera();

        cam.translateXProperty().set(-800);
        cam.translateYProperty().set(-400);

        cam.translateZProperty().set(-500);
        cam.setNearClip(1);
        cam.setFarClip(1000);
        //Group
        Group group = new Group(nodesGroupe, menu);
        //Scene
        Scene scene3D = new Scene(group, 1280, 720);
        scene3D.setCamera(cam);
        // //Move
        // stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
        //	switch (event.getCode()) {
        //	case Q:
        //	sphere.translateZProperty().set(sphere.getTranslateZ() + 10);
        //	break;
        //	case E:
        //	sphere.translateZProperty().set(sphere.getTranslateZ() - 10);
        //	break;
        //	case W:
        //	sphere.translateYProperty().set(sphere.getTranslateY() - 10);
        //	break;
        //	case S:
        //	sphere.translateYProperty().set(sphere.getTranslateY() + 10);
        //	break;
        //	case A:
        //	sphere.translateXProperty().set(sphere.getTranslateX() - 10);
        //	break;
        //	case D:
        //	sphere.translateXProperty().set(sphere.getTranslateX() + 10);
        //	break;
        //	default:
        //	break;
        //	}
        // }); stage.setScene(scene3D); stage.show();
    }
    class UpdateButton implements EventHandler < ActionEvent > {
        @Override
        public void handle(ActionEvent activeEvent) {
            nodesGroupe.DeleteAll();
            Line lineX = new Line(-600, 0, 600, 0);
            lineX.setStrokeWidth(5);
            Line lineY = new Line(0, -600, 0, 600);
            lineY.setStrokeWidth(5);

            nodesGroupe.getChildren().add(lineX);
            nodesGroupe.getChildren().add(lineY);
            build3D();
        }
    }
    class ResetButton implements EventHandler < ActionEvent > {
        @Override
        public void handle(ActionEvent activeEvent) {
            P00X.setText("0,0");
            P00Y.setText("0,0");
            P00Z.setText("1,0");
            P01X.setText("1,0");
            P01Y.setText("1,0");
            P01Z.setText("1,0");
            P10X.setText("1,0");
            P10Y.setText("0,0");
            P10Z.setText("0,0");
            P11X.setText("0,0");
            P11Y.setText("1,0");
            P11Z.setText("0,0");

            nodesGroupe.DeleteAll();
            Line lineX = new Line(-600, 0, 600, 0);
            lineX.setStrokeWidth(5);
            Line lineY = new Line(0, -600, 0, 600);
            lineY.setStrokeWidth(5);


            nodesGroupe.getChildren().add(lineX);
            nodesGroupe.getChildren().add(lineY);
            build3D();
        }
    }
    public void build3D() {
        //Построение плоскости
        ArrayList < Double > P1X = new ArrayList < > ();
        String[] k = P00X.getText().split(",");
        P1X.add(Double.parseDouble(k[0]));
        P1X.add(Double.parseDouble(k[1]));
        ArrayList < Double > P1Y = new ArrayList < > ();
        k = P00Y.getText().split(",");
        P1Y.add(Double.parseDouble(k[0]));
        P1Y.add(Double.parseDouble(k[1]));
        ArrayList < Double > P1Z = new ArrayList < > ();
        k = P00Z.getText().split(",");
        P1Z.add(Double.parseDouble(k[0]));
        P1Z.add(Double.parseDouble(k[1]));

        ArrayList < Double > P2X = new ArrayList < > ();
        k = P01X.getText().split(",");
        P2X.add(Double.parseDouble(k[0]));
        P2X.add(Double.parseDouble(k[1]));
        ArrayList < Double > P2Y = new ArrayList < > ();
        k = P01Y.getText().split(",");
        P2Y.add(Double.parseDouble(k[0]));
        P2Y.add(Double.parseDouble(k[1]));
        ArrayList < Double > P2Z = new ArrayList < > ();
        k = P01Z.getText().split(",");
        P2Z.add(Double.parseDouble(k[0]));
        P2Z.add(Double.parseDouble(k[1]));

        ArrayList < Double > P3X = new ArrayList < > ();
        k = P10X.getText().split(",");
        P3X.add(Double.parseDouble(k[0]));
        P3X.add(Double.parseDouble(k[1]));
        ArrayList < Double > P3Y = new ArrayList < > ();
        k = P10Y.getText().split(",");
        P3Y.add(Double.parseDouble(k[0]));
        P3Y.add(Double.parseDouble(k[1]));
        ArrayList < Double > P3Z = new ArrayList < > ();
        k = P10Z.getText().split(",");
        P3Z.add(Double.parseDouble(k[0]));
        P3Z.add(Double.parseDouble(k[1]));

        ArrayList < Double > P4X = new ArrayList < > ();
        k = P11X.getText().split(",");
        P4X.add(Double.parseDouble(k[0]));
        P4X.add(Double.parseDouble(k[1]));
        ArrayList < Double > P4Y = new ArrayList < > ();
        k = P11Y.getText().split(",");
        P4Y.add(Double.parseDouble(k[0]));
        P4Y.add(Double.parseDouble(k[1]));
        ArrayList < Double > P4Z = new ArrayList < > ();
        k = P11Z.getText().split(",");
        P4Z.add(Double.parseDouble(k[0]));
        P4Z.add(Double.parseDouble(k[1]));
        //P1

        ArrayList < Double > P1 = new ArrayList < > ();
        P1.add(P1X.get(0) - P1X.get(1)); //0
        P1.add(P1Y.get(0) - P1Y.get(1)); //0
        P1.add(P1Z.get(0) - P1Z.get(1)); //1
        //P2
        ArrayList < Double > P2 = new ArrayList < > ();
        P2.add(P2X.get(0) - P2X.get(1)); //1
        P2.add(P2Y.get(0) - P2Y.get(1)); //1
        P2.add(P2Z.get(0) - P2Z.get(1)); //1
        //P3
        ArrayList < Double > P3 = new ArrayList < > ();
        P3.add(P3X.get(0) - P3X.get(1)); //1
        P3.add(P3Y.get(0) - P3Y.get(1)); //0
        P3.add(P3Z.get(0) - P3Z.get(1)); //0
        //P4
        ArrayList < Double > P4 = new ArrayList < > ();
        P4.add(P4X.get(0) - P4X.get(1)); //0
        P4.add(P4Y.get(0) - P4Y.get(1)); //1
        P4.add(P4Z.get(0) - P4Z.get(1)); //0

        for (double w = 0; w <= 1; w += 0.01) {
            for (double u = 0; u <= 1; u += 0.01) {
                //x y z
                double p1k = (1 - u) * (1 - w);
                ArrayList < Double > P1New = new ArrayList < > ();
                for (int i = 0; i < 3; i++) {
                    P1New.add(P1.get(i) * p1k);
                }
                double p2k = (1 - u) * w;
                ArrayList < Double > P2New = new ArrayList < > ();
                for (int i = 0; i < 3; i++) {
                    P2New.add(P2.get(i) * p2k);
                }
                double p3k = u * (1 - w);
                ArrayList < Double > P3New = new ArrayList < > ();
                for (int i = 0; i < 3; i++) {
                    P3New.add(P3.get(i) * p3k);
                }
                double p4k = u * w;
                ArrayList < Double > P4New = new ArrayList < > ();
                for (int i = 0; i < 3; i++) {
                    P4New.add(P4.get(i) * p4k);
                }

                double x = P1New.get(0) + P2New.get(0) + P3New.get(0) + P4New.get(0);
                double y = P1New.get(1) + P2New.get(1) + P3New.get(1) + P4New.get(1);
                double z = P1New.get(2) + P2New.get(2) + P3New.get(2) + P4New.get(2);
                SmartSphere sph = new SmartSphere(8);
                int kaf = 500;
                System.out.println(x + " " + y + " " + z);
                sph.setXYZ((int) Math.floor(x * kaf), (int) Math.floor(y * kaf), (int) Math.floor(z * kaf));
                nodesGroupe.getChildren().add(sph);
            }
        }
    }
    class SmartSphere extends Sphere {
        SmartSphere(int v) {
            this.setRadius(v);
        }
        void setXYZ(int i, int j, int k) {
            this.setTranslateX(i);
            this.setTranslateY(j);
            this.setTranslateZ(k);
        }
    }

    public static void multiplyС(Double[] a, Double k) {
        int s = a.length;
        for (int i = 0; i < s; ++i) a[i] = a[i] * k;
    }
    class SmartGroup extends Group {
        Rotate r;
        Transform t = new Rotate();
        void rotateByX(Double ang) {
            r = new Rotate(ang, Rotate.X_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }
        void rotateByY(Double ang) {
            r = new Rotate(ang, Rotate.Y_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }
        void DeleteAll() {
            this.getChildren().clear();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}