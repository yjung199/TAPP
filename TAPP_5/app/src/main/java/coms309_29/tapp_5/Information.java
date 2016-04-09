package coms309_29.tapp_5;

public class Information {

    private String title;
    private String description;
    private int thumbnail_ID;
    private boolean isSelected;

    public Information()
    {

    }

    public Information(String title, String description)
    {
        this.title = title;
        this.description = description;

    }

    public Information(String title, String description, boolean isSelected)
    {
        this.title = title;
        this.description = description;
        this.isSelected = isSelected;
    }

    public Information(String title, String description, boolean isSelected, int thumbnail)
    {
        this.title = title;
        this.description = description;
        this.isSelected = isSelected;
        this.thumbnail_ID = thumbnail;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


    public void setThumbnail(int thumbnail) {
        this.thumbnail_ID = thumbnail;
    }

    public int getThumbnail() {
        return thumbnail_ID;
    }
}
