package ibf2022csf.upload.uploadfiletos3.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Post implements Serializable{
    private String complain;
    private String title;
    // potential problem??
    // private byte[] image;
    private String imageUrl;
    private Integer postId;

    public String getComplain() {
        return complain;
    }
    public void setComplain(String complain) {
        this.complain = complain;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    // public byte[] getImage() {
    //     return image;
    // }
    // public void setImage(byte[] image) {
    //     this.image = image;
    // }
    public Integer getPostId() {
        return postId;
    }
    public void setPostId(Integer postId) {
        this.postId = postId;
    }
     public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Post populate(ResultSet rs) throws SQLException{
        final Post p = new Post();
        p.setPostId(rs.getInt("id"));
        p.setComplain(rs.getString("complain"));
        p.setTitle(rs.getString("title"));
        p.setImageUrl(rs.getString("imageUrl"));
        return p;
    }
   
    

}