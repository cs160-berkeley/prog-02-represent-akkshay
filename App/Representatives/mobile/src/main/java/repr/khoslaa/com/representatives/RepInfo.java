package repr.khoslaa.com.representatives;

/**
 * Created by akkshay on 3/13/16.
 */
public class RepInfo {
    private String fullName;
    private String website;
    private String email;
    private String tweet;
    private String party;
    private String termEnds;

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    private String twitterId;


    private String repId;



    public RepInfo() {
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }


    public String getTermEnds() {
        return termEnds;
    }

    public void setTermEnds(String termEnds) {
        this.termEnds = termEnds;
    }
    public String getRepId() {
        return repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }




}
