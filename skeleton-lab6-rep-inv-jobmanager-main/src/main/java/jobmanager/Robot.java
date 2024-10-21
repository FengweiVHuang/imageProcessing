package jobmanager;

/**
 * This immutable class represents a real-world Robot, which has a unique positive integer id. Any Robot instance with
 * id = 0 does not represent a real-world robot and is called a Null Robot. No Null Robot is equal to any other
 * robot -- not even to other Null Robots.
 */

public class Robot{

    public final int id;

    /**
     * Creates a new Robot with the specified id.
     *
     * If id = 0, an instance of a Null Robot is created.
     *
     * @param id
     * @throws IllegalArgumentException if id < 0
     */
    public Robot(int id)
    {
        if (id < 0) { throw new IllegalArgumentException("Robot id must be strictly positive or 0 for a Null Robot"); }
        this.id = id;
    }

    /**
     * Checks if this Robot is a Null Robot
     *
     * @return true iff this.id = 0
     */
    public boolean isNull() {
        return id == 0;
    }

    /**
     * Compares the specified object with this Robot for equality
     *
     * @param o
     * @return true iff this and o represent the same real-world Robot as determined by their ids
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Robot)) { return false; }
        Robot other = (Robot) o;
        if (this.id == 0 || other.id == 0) { return false; }
        return this.id == other.id;
    }

    /**
     * Returns the hashcode for this Robot
     *
     * @return the hashcode value which is the id of this
     */
    @Override
    public int hashCode() {
        return id;
    }

}
