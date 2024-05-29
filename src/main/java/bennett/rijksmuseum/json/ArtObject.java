package bennett.rijksmuseum.json;

public class ArtObject {

    // title, longTitle, webImage and principalOrFirstMaker
    public String title;

    public String principalOrFirstMaker;

    public String longTitle;

    public ArtImage webImage;

    public String getArtist() {
        return principalOrFirstMaker;
    }

    public String getImageUrl() {
        return webImage != null ? webImage.getUrl() : null;
    }

    public ArtImage getWebImage() {
        return webImage;
    }

    public String getTitle() {
        return title;
    }

}
