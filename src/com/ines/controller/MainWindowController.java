package com.ines.controller;

import com.ines.EmailManager;
import com.ines.controller.services.MessageRenderService;
import com.ines.model.EmailMessage;
import com.ines.model.EmailTreeItem;
import com.ines.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class MainWindowController extends BaseController implements Initializable {

    private MenuItem markUnreadMenuItem = new MenuItem("mark as unread");
    private MenuItem deleteMessageMenuItem = new MenuItem("delete message");

    @FXML
    private TreeView<String> mailTreeView;

    @FXML
    private TableView<EmailMessage> mailsTableView;

    @FXML
    private TableColumn<EmailMessage, String> senderColumn;

    @FXML
    private TableColumn<EmailMessage, String> recipientColumn;

    @FXML
    private TableColumn<EmailMessage, String> subjectColumn;

    @FXML
    private TableColumn<EmailMessage, Integer> sizeColumn;

    @FXML
    private TableColumn<EmailMessage, Date> dateColumn;

    @FXML
    private WebView mailWebView;

    private MessageRenderService messageRenderService;

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void openOptionsWindow() {
        viewFactory.showOptionWindow();
    }

    @FXML
    void addAccountAction(){
        viewFactory.showLoginWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpEmailTableView();
        setUpFolderTreeView();
        setUpFolderSelection();
        setUpMessageRenderService();
        setUpMessageSelection();
        setUpContextMenus();
    }

    private void setUpContextMenus() {
        markUnreadMenuItem.setOnAction(event -> {
            emailManager.setUnRead();
        });
        deleteMessageMenuItem.setOnAction(event -> {
            emailManager.deleteSelectedMessage();
            mailWebView.getEngine().loadContent("");
        });
    }

    private void setUpMessageSelection() {
        mailsTableView.setOnMouseClicked(event -> {
            EmailMessage emailMessage = mailsTableView.getSelectionModel().getSelectedItem();
            if(emailMessage != null){
                emailManager.setSelectedMessage(emailMessage);
                if(!emailMessage.isRead()){
                    emailManager.setRead();
                }
                messageRenderService.setEmailMessage(emailMessage);
                messageRenderService.restart();
            }
        });
    }

    private void setUpMessageRenderService() {
        messageRenderService = new MessageRenderService(mailWebView.getEngine());
    }

/*    private void setUpMessageRenderService() {
        mailsTableView.setOnMouseClicked(event -> {
            EmailMessage emailMessage = mailsTableView.getSelectionModel().getSelectedItem();
            if(emailMessage != null){
                messageRenderService.setEmailMessage(emailMessage);
                messageRenderService.restart();
            }
        });
    }*/


    private void setUpFolderSelection() {
        mailTreeView.setOnMouseClicked(e->{
            EmailTreeItem<String> item = (EmailTreeItem<String>)mailTreeView.getSelectionModel().getSelectedItem();
            if (item != null) {
                emailManager.setSelectedFolder(item);
                mailsTableView.setItems(item.getEmailMessages());
            }
        });
    }


    private void setUpEmailTableView() {
        senderColumn.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("sender")));
        subjectColumn.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("subject")));
        recipientColumn.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("recipient")));
        sizeColumn.setCellValueFactory((new PropertyValueFactory<EmailMessage, Integer>("size")));
        dateColumn.setCellValueFactory((new PropertyValueFactory<EmailMessage, Date>("receivedDate")));


        mailsTableView.setContextMenu(new ContextMenu(markUnreadMenuItem, deleteMessageMenuItem));

    }

    private void setUpFolderTreeView() {
        mailTreeView.setRoot(emailManager.getRootFolder());
        mailTreeView.setShowRoot(false);
        setUpBoldRows();
    }

    private void setUpBoldRows() {
        mailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> param) {
                return new TableRow<EmailMessage>(){
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty){
                        super.updateItem(item, empty);
                        if(item != null) {
                            if(item.isRead()){
                                setStyle("");
                            } else {
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }
}
