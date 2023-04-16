package droubay.sfwr1qkm2droubay2;


/**
 * @author Danielle Droubay
 * @version 1.0
 */
public class InHouse extends Part {

    /**
     * Class members
     */

    // machineId specifies the machineId for an InHouse instantiation of the abstract class Part
    private int machineId;

    /**
     * Constructor for an InHouse Part
     * Creates a new InHouse object built of the following parameters
     * @param id the id of the InHouse object
     * @param name the name of the InHouse object
     * @param price the price of the InHouse object
     * @param stock the stock (or inventory) of the InHouse object
     * @param min the minimum of the InHouse object
     * @param max the maximum of the InHouse object
     * @param machineId the machineId of the InHouse object
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        // This concrete child InHouse inherits from abstract parent Part
        super(id, name, price, stock, min, max);
        this.machineId = machineId;

    }

    /**
     *
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId){

        this.machineId = machineId;
    }

    /**
     *
     * @return the machineId
     */
    public int getMachineId(){
        return machineId;
    }
}
