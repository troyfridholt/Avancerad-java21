package app;
	


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


public class Main extends Application {
	
	@Override
 	public void start(Stage primaryStage) throws Exception {
		try {
			
			
			JSONReader jsonr = new JSONReader();
		
			CSVReader csvr = new CSVReader();
			
			
			
			Button b1 = new Button("Ladda CSV");
			Button b2 = new Button("Ladda JSON");
			
			
			EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					
					BorderPane root = new BorderPane();
					
					TableView table = new TableView<CSVBean>();
					TableColumn orderDateCol = new TableColumn<CSVBean, String>("ORDERDATE");
					orderDateCol.setCellValueFactory(new PropertyValueFactory<CSVBean, String>("orderDate"));
					
					TableColumn regionCol = new TableColumn<CSVBean, String>("REGION");
					regionCol.setCellValueFactory(new PropertyValueFactory<CSVBean, String>("region"));
					
					TableColumn rep1Col = new TableColumn<CSVBean, String>("REP1");
					rep1Col.setCellValueFactory(new PropertyValueFactory<CSVBean, String>("rep1"));
					
					TableColumn rep2Col = new TableColumn<CSVBean, String>("REP2");
					rep2Col.setCellValueFactory(new PropertyValueFactory<CSVBean, String>("rep2"));
					
					TableColumn itemCol = new TableColumn<CSVBean, String>("ITEM");
					itemCol.setCellValueFactory(new PropertyValueFactory<CSVBean, String>("item"));
					
					TableColumn unitsCol = new TableColumn<CSVBean, Integer>("UNITS");
					unitsCol.setCellValueFactory(new PropertyValueFactory<CSVBean, Integer>("units"));
					
					TableColumn unitCostCol = new TableColumn<CSVBean, String>("UNITCOST");
					unitCostCol.setCellValueFactory(new PropertyValueFactory<CSVBean, String>("unitCost"));
					
					TableColumn totalCol = new TableColumn<CSVBean, String>("TOTAL");
					totalCol.setCellValueFactory(new PropertyValueFactory<CSVBean, String>("total"));
					
					table.getColumns().add(orderDateCol);
					table.getColumns().add(regionCol);
					table.getColumns().add(rep1Col);
					table.getColumns().add(rep2Col);
					table.getColumns().add(itemCol);
					table.getColumns().add(unitsCol);
					table.getColumns().add(unitCostCol);
					table.getColumns().add(totalCol);
					
					table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
					
					
					
					for(int i=1; i<= csvr.getOrderDate().size()-1; i++) {
						int unitsInt = Integer.parseInt(csvr.getUnits().get(i));
						
					table.getItems().add(new CSVBean(
							csvr.getOrderDate().get(i), 
							csvr.getRegion().get(i), 
							csvr.getRep1().get(i), 
							csvr.getRep2().get(i),
							csvr.getItem().get(i),
							unitsInt,
							csvr.getUnitCost().get(i),
							csvr.getTotal().get(i)));
					}
				
					root.setCenter(table);
					
					Scene scene = new Scene(root, 800, 800);
					primaryStage.setTitle("TableView CSV");
					primaryStage.setScene(scene);
					primaryStage.show();
					
					
				}	

			};
	
			EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					BorderPane root = new BorderPane();
					
					TableView table = new TableView<JSONBean>();
					TableColumn orderDateCol = new TableColumn<JSONBean, String>("ORDERDATE");
					orderDateCol.setCellValueFactory(new PropertyValueFactory<JSONBean, String>("orderDate"));
					
					TableColumn regionCol = new TableColumn<JSONBean, String>("REGION");
					regionCol.setCellValueFactory(new PropertyValueFactory<JSONBean, String>("region"));
					
					TableColumn rep1Col = new TableColumn<JSONBean, String>("REP1");
					rep1Col.setCellValueFactory(new PropertyValueFactory<JSONBean, String>("rep1"));
					
					TableColumn rep2Col = new TableColumn<JSONBean, String>("REP2");
					rep2Col.setCellValueFactory(new PropertyValueFactory<JSONBean, String>("rep2"));
					
					TableColumn itemCol = new TableColumn<JSONBean, String>("ITEM");
					itemCol.setCellValueFactory(new PropertyValueFactory<JSONBean, String>("item"));
					
					TableColumn unitsCol = new TableColumn<JSONBean, Integer>("UNITS");
					unitsCol.setCellValueFactory(new PropertyValueFactory<JSONBean, Integer>("units"));
					
					TableColumn unitCostCol = new TableColumn<JSONBean, String>("UNITCOST");
					unitCostCol.setCellValueFactory(new PropertyValueFactory<JSONBean, String>("unitCost"));
					
					TableColumn totalCol = new TableColumn<JSONBean, String>("TOTAL");
					totalCol.setCellValueFactory(new PropertyValueFactory<JSONBean, String>("total"));
					
					table.getColumns().add(orderDateCol);
					table.getColumns().add(regionCol);
					table.getColumns().add(rep1Col);
					table.getColumns().add(rep2Col);
					table.getColumns().add(itemCol);
					table.getColumns().add(unitsCol);
					table.getColumns().add(unitCostCol);
					table.getColumns().add(totalCol);
					
					table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
					
					
					
					for(int i=1; i<= jsonr.getOrderDate().size()-1; i++) {
						int unitsInt = Integer.parseInt(csvr.getUnits().get(i));
						
					table.getItems().add(new JSONBean(
							jsonr.getOrderDate().get(i),
							jsonr.getRegion().get(i), 
							jsonr.getRep1().get(i), 
							jsonr.getRep2().get(i),
							jsonr.getItem().get(i),
							unitsInt,
							jsonr.getUnitCost().get(i),
							jsonr.getTotal().get(i)));
					}
				
					root.setCenter(table);
					
					Scene scene = new Scene(root, 800, 800);
					primaryStage.setTitle("JSON");
					primaryStage.setScene(scene);
					primaryStage.show();
					
				}
			};
			b2.setLayoutX(100);
			
			b1.setOnAction(event1);
			b2.setOnAction(event2);
			
			
			
			primaryStage.setTitle("Uppgift 2");
			Group root = new Group();
			Scene scene = new Scene (root);
			primaryStage.setScene(scene);
			
			
			root.getChildren().add(b1);
			root.getChildren().add(b2);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
		
		}
}