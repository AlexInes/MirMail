package com.ines;

import com.ines.controller.services.FetchFoldersService;
import com.ines.controller.services.FolderUpdateService;
import com.ines.model.EmailMessage;
import com.ines.model.EmailTreeItem;
import com.ines.model.MailAccount;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    private FolderUpdateService folderUpdateService;
    private List<Folder> folderList = new ArrayList<Folder>();

    private EmailTreeItem<String> rootFolder = new EmailTreeItem<String>("");


    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }


    public EmailManager(){
        folderUpdateService = new FolderUpdateService(folderList);
        folderUpdateService.start();
    }


    public EmailTreeItem<String> getRootFolder() {
        return rootFolder;
    }

    public void addEmailAccount(MailAccount mailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(mailAccount.getEmail());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(mailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        rootFolder.getChildren().add(treeItem);
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public void setRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementMessagesCount();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setUnRead() {
        try {
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage() {
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
