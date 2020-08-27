package com.taku;

import com.taku.controller.services.FetchFolderService;
import com.taku.controller.services.FolderUpdaterService;
import com.taku.model.EmailAccount;
import com.taku.model.EmailMessage;
import com.taku.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    // folder handling;

    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    private List<Folder> folderList = new ArrayList<Folder>();

    private FolderUpdaterService folderUpdaterService;

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

    public List<Folder> getFolderList() {
        return folderList;
    }

    public EmailManager(){
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getAddress());
        treeItem.setExpanded(true);
        FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(), treeItem, folderList);

        fetchFolderService.start();
        foldersRoot.getChildren().add(treeItem);
    }

    public void setRead() {
        try{
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementMessagesCount();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setUnRead() {
        try{
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementMessagesCount();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage() {
        try{
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
