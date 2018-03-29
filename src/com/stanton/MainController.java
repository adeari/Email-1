package com.stanton;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

public class MainController implements Initializable{
	@FXML
    private TreeView<String> emailFoldersTreeView;
	private TreeItem<String> root = new TreeItem<>();
	private SampleData sampleData = new SampleData();
	
	@FXML
    private TableView<EmailMessageBean> emailTableView;
	
	@FXML
	private TableColumn<EmailMessageBean, String> subjectCol;

	@FXML
	private TableColumn<EmailMessageBean, String> senderCol;

    @FXML
	private TableColumn<EmailMessageBean, String> sizeCol;

	@FXML
    private Button Button1;

    @FXML
    private WebView messageRenderer;

    @FXML
    void Button1Action(ActionEvent event) {
    		System.out.println("Button1 clicked!");
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("size"));
		
		
		sizeCol.setComparator(new Comparator<String>(){
			Integer int1, int2;
			
			@Override
			public int compare(String o1, String o2) {
				int1 = EmailMessageBean.formatedValues.get(o1);
				int2 = EmailMessageBean.formatedValues.get(o2);
				
				return int1.compareTo(int2);
			}
			
		});
		
		emailFoldersTreeView.setRoot(root);
		
		root.setValue("example@yahoo.com");
		root.setGraphic(resolveIcon(root.getValue()));
		
		TreeItem<String> inbox = new TreeItem<>("Inbox",resolveIcon("Inbox"));
		TreeItem<String> sent = new TreeItem<>("Sent", resolveIcon("Sent"));
			TreeItem<String> subItem1 = new TreeItem<>("Sub-Item1",resolveIcon("SubItem1"));
			TreeItem<String> subItem2 = new TreeItem<>("Sub-Item2",resolveIcon("SubItem2"));
			sent.getChildren().addAll(subItem1, subItem2);
		TreeItem<String> spam = new TreeItem<>("Spam",resolveIcon("Spam"));
		TreeItem<String> trash = new TreeItem<>("Trash",resolveIcon("Trash"));
		
		root.getChildren().addAll(inbox, sent, spam, trash);
		root.setExpanded(true);
		emailFoldersTreeView.setOnMouseClicked(e ->{
			TreeItem<String> item = emailFoldersTreeView.getSelectionModel().getSelectedItem();
			if(item != null) {
				emailTableView.setItems(sampleData.emailFolders.get(item.getValue()));
			}
		});
		}
	
	private Node resolveIcon(String treeItemValue){
		String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
		ImageView returnIcon;
			try {
				if(lowerCaseTreeItemValue.contains("inbox")) {
					returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/inbox.png")));
				}
				else if(lowerCaseTreeItemValue.contains("sent")) {
					returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/sent2.png")));
				}
				else if(lowerCaseTreeItemValue.contains("spam")) {
					returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/spam.png")));
				}
				else if(lowerCaseTreeItemValue.contains("@")) {
					returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/email.png")));
				}
				else {
					returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/folder.png")));
				}
			} catch (NullPointerException e) {
				System.out.println("Invalid image location");
				e.printStackTrace();
				returnIcon = new ImageView();
			}
			
			returnIcon.setFitHeight(16);
			returnIcon.setFitWidth(16);
		return returnIcon;
	}

}
