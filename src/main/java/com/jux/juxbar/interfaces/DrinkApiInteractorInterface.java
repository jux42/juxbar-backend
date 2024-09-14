package com.jux.juxbar.interfaces;

public interface DrinkApiInteractorInterface {

    void checkUpdateAndDownload() throws InterruptedException;

    void downloadImages();
    void downloadPreviews();
}
