package ImageHoster.service;

import ImageHoster.model.Image;
import ImageHoster.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    //Call the getAllImages() method in the Repository and obtain a List of all the images in the database
    public List<Image> getAllImages() {
        return imageRepository.getAllImages();
    }


    //The method calls the createImage() method in the Repository and passes the image to be persisted in the database
    public void uploadImage(Image image) {
        imageRepository.uploadImage(image);
    }


    //The method calls the getImageByIdAndTitle() method in the Repository and passes the id and title of the image to be fetched
    public Image getImageByIdAndTitle(Integer id, String title) {
        return imageRepository.getImageByIdAndTitle(id,title);
    }

    //The method calls the getImage() method in the Repository and passes the id of the image to be fetched
    public Image getImage(Integer imageId) {
        return imageRepository.getImage(imageId);
    }

    //The method calls the updateImage() method in the Repository and passes the Image to be updated in the database
    public void updateImage(Image updatedImage) {
        imageRepository.updateImage(updatedImage);
    }

    //The method calls the deleteImage() method in the Repository and passes the Image id of the image to be deleted in the database
    public void deleteImage(Integer imageId) {
        imageRepository.deleteImage(imageId);
    }

    //The method calls the imageOwner() method in the Repository and passes the Image id of the image
    //and the session info to validate the current user is the owner of the image.
    public boolean imageOwner(Integer imageId, HttpSession session) {
        return imageRepository.imageOwner(imageId, session);
    }
}
