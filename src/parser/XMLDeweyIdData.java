package parser;

public class XMLDeweyIdData 
{
    /**
     * Dewey id and string content of node
     */
    private String deweyId, strContent;

    public XMLDeweyIdData(String deweyId, String strContent)
    {
        this.deweyId = deweyId;
        this.strContent = strContent;
    }

    /**
     * Get dewey id
     * @return
     */
    public String GetDeweyId()
    {
        return this.deweyId;
    }
    
    /**
     * Get content
     * @return
     */
    public String GetContent()
    {
        return this.strContent;
    }
}
