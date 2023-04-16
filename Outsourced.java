package droubay.sfwr1qkm2droubay2;

/**
 * @author Danielle Droubay
 * @version 1.0
 */
public class Outsourced extends Part{
    /**
     * Class members
     */

    // companyName specifies the companyName for an Outsourced instantiation of the abstract class Part
    private String companyName;

    /**
     * Constructor for an Outsourced Part
     * Creates a new Outsourced object built of the following parameters
     * @param id the id of the Outsourced object
     * @param name the name of the Outsourced object
     * @param price the price of the Outsourced object
     * @param stock the stock (or inventory) of the Outsourced object
     * @param min the minimum of the Outsourced object
     * @param max the maximum of the Outsourced object
     * @param companyName the companyName of the Outsourced object
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        // This concrete child Outsourced inherits from abstract parent Part
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /**
     *
     * @param companyName the company name to set
     */

    public void setCompanyName (String companyName){

        this.companyName = companyName;
    }

    /**
     *
     * @return the company name
     */

    public String getCompanyName(){
        return companyName;
    }
}
