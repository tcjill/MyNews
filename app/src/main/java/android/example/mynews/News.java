package android.example.mynews;


/**
 *A {@LINK News} object has info related to a single arizona news article.
 */
public class News {
    /** the name of the guardian section with the Arizona news in it*/
    private String mNewsArticleSectionName;

    /**title of the article*/
    private String mNewsArticleTitle;

    /**the author*/
    private String mNewsArticleAuthor;

    /**Publication date*/
    private String mNewsArticlePublicationDate;
    /**web url*/
    private String mNewsArticleUrl;

    /**
     *          @param NewsArticleSectionName is the name of The Guardian section that the
     *      * news article appears in
     *      * @param NewsArticleTitle is the title of the news article
     *      * @param NewsArticleAuthor is the author of the news article
     *      * @param NewsArticlePublicationDate is the news_article_publication_date that the news article was published
     *      * @param NewsArticleUrl is the website URL for the Guardian news article
     *      */


    public News(String NewsArticleSectionName, String NewsArticleTitle, String NewsArticleAuthor,
            String NewsArticlePublicationDate, String NewsArticleUrl) {
        mNewsArticleSectionName = NewsArticleSectionName;
        mNewsArticleTitle = NewsArticleTitle;
        mNewsArticleAuthor = NewsArticleAuthor;
        mNewsArticlePublicationDate = NewsArticlePublicationDate;
        mNewsArticleUrl = NewsArticleUrl;
    }


    /**
     * Returns the name of The Guardian section that the guardian
     * news article appears in.
     */
    public String getNewsArticleSectionName() {
        return mNewsArticleSectionName;
    }
    /**
     * Returns the title of the news article.
     */
    public String getNewsArticleTitle() {
        return mNewsArticleTitle;
    }
    /**
     * Returns the title of the author of the news article.
     */
    public String getNewsArticleAuthor() {
        return mNewsArticleAuthor;
    }
    /**
     * Returns the news_article_publication_date the news article was published.
     */
    public String getNewsArticlePublicationDate() {
        return mNewsArticlePublicationDate;
    }
    /**
     * Returns the website URL for the article.
     */
    public String getNewsArticleUrl() {
        return mNewsArticleUrl;
    }
}
