package jobmanager;

import java.util.*;

/**
 * This class represents a JobManager that is capable of managing a set of jobs & their assignment to real-world
 * Robots.
 *
 * Jobs are identified by a unique positive integer. A null job is a job with id = 0 and does not represent any work to
 * be completed on a real-world Robot. Where n1 and n2 are two job IDs satisfying n1 < n2, job n2 has a higher priority
 * than job n1.
 *
 * A JobManager does not add/remove/manage Null Robots, and it does not assign jobs to a Null Robot.
 * @see Robot
 */

public class JobManager {

    /**
     * DISCLAIMER: The fields in this class & the checkRep() method are public only to help us test your implementation.
     * In practice, all fields & the checkRep() method should be private to prevent rep exposure.
     */

    final public Map<Robot, TreeSet<Integer>> robotToJobs = new HashMap<>();
    // maps: Robot --> ordered set of jobs assigned to Robot using the natural (non-decreasing) order on Integers

    final public Set<Integer> unassignedJobs = new HashSet<>();
    // set of unassigned jobs

    public int n;
    // number of jobs in this; also, the id of highest priority job in this

    /**
     * How does our data representation (robotToJobs, unassignedJobs, n) represent a JobManager?
     *
     * (1) All jobs managed by this are represented as Integers, where the Integer corresponds to the job's id.
     * (2) The highest priority job in this has id = this.n.
     * (3) This manages a job with id = Z iff Z is an Integer in [1,...,this.n].
     * (4) This manages a job with id = Z iff Z is in this.unassignedJobs or some TreeSet from robotToJobs.valueSet().
     * (5) A job is unassigned iff it is in this.unassignedJobs.
     * (6) A robot is managed by this iff it is a key in this.robotToJobs.
     * (7) A job is assigned to robot R iff it is in the TreeSet corresponding to R in this.robotToJobs, i.e., the job
     *     is in this.robotToJobs.get(R).
     * (8) A robot R has no assigned jobs iff this.robotToJobs.get(R) is empty.
     * (9) If robot R is assigned at least one job, the highest priority job assigned to robot R is represented as
     *     this.robotToJobs.get(R).last().
     *
     * @see java.util.TreeSet
     */

    /**
     * Creates a new JobManager to manage n jobs with job IDs [1,...,n].
     *
     * @param n
     * @throws IllegalArgumentException if n < 1
     */
    public JobManager(int n) {
        if (n < 1) {throw new IllegalArgumentException("n must be at least 1");}
        this.n = n;
        for (int i = 1; i < n+1; i++) {
            this.unassignedJobs.add(i);
        }
    }

    /**
     * Checks if this JobManager has the specified Robot
     *
     * @param robot
     * @return true if robot is now in this, and false otherwise
     */
    public boolean hasRobot(Robot robot) {

        if (robot == null || robot.isNull()) { return false; }
        checkRep();
        return this.robotToJobs.containsKey(robot);

    }

    /**
     * Adds the specified Robot to this JobManager
     *
     * @param robot
     *
     * @return true if this formerly did not have robot but now does, and false otherwise
     */
    public boolean addRobot(Robot robot) {
        if (robot == null || robot.isNull()) { return false; }
        if (!this.robotToJobs.containsKey(robot)) {
            this.robotToJobs.put(robot, new TreeSet<>());

            return true;
        }
        checkRep();
        return false;
    };

    /**
     * Removes the specified robot from this JobManager
     *
     * @param robot
     * @return true if this formerly did have robot but now does not, and false otherwise
     * @post All jobs formerly assigned to this robot are now unassigned
     */
    public boolean removeRobot(Robot robot) {
        if (robot == null || robot.isNull()) { return false; }
        if (this.robotToJobs.containsKey(robot)) {
            this.robotToJobs.remove(robot);
            return true;
        }
        checkRep();
        return false;
    };

    /**
     * Assigns all unassigned jobs managed by this JobManager with an id <= the specified id to the specified robot when
     * this has the specified robot
     *
     * @param robot
     * @param jobId
     * @return true if both of the following hold true: (1) this has robot, and (2) all formerly unassigned jobs managed
     *         by this whose id <= jobId are now assigned to robot; and false otherwise
     *
     * N.B., this method returns false only if either (1) this does not have robot, or (2) there exists some formerly
     * unassigned job managed by this with id <= jobId that is currently not assigned to robot.
     */
    public boolean assignJobs(Robot robot, int jobId) {
        if (robot == null || robot.isNull()) { return false; }
        if (!this.robotToJobs.containsKey(robot)) { return false; }
        Set<Integer> jobsToRemove = new HashSet<>();

        for (Integer job : this.unassignedJobs) {
            if (job <= jobId) {
                this.robotToJobs.get(robot).add(job);
                jobsToRemove.add(job);
            }
        }

        this.unassignedJobs.removeAll(jobsToRemove);

        checkRep();

        return true;
    }

    /**
     * Checks if a specified job is assigned to a robot managed by this JobManager
     *
     * @param jobId
     * @return true if both of the following hold true: (1) a job with id = jobId is managed by this, and (2) that
     *         job is assigned to a robot in this; and false otherwise
     */
    public boolean isAssigned(int jobId) {
        if (jobId < 1 || jobId > n) {
            return false;
        }

        if (this.unassignedJobs.contains(jobId)) {
            return false;
        }

        for (Set<Integer> jobs : this.robotToJobs.values()) {
            if (jobs.contains(jobId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the Robot in this JobManager assigned the job with the specified id
     *
     * @param jobId
     * @return the Robot assigned job with id = jobId if both of the following hold true: (1) a job with id = JobId is
     *         managed by this, and (2) that job is assigned to Robot in this; and a Null Robot otherwise
     */
    public Robot getRobot(int jobId) {
        if (jobId < 1 || jobId > this.n) { return new Robot(0); }
        for (Robot robot : this.robotToJobs.keySet()) {
            if (this.robotToJobs.get(robot).contains(jobId)) { return new Robot(robot.id); }
        }
        checkRep();
        return new Robot(0);
    }

    /**
     * Moves all jobs managed by this JobManager with an id <= the specified id that were assigned to a specified robot
     * to another specified robot when this has both specified robots
     *
     * @param srcRobot
     * @param dstRobot
     * @param jobId
     * @return true if both of the following hold true: (1) this has srcRobot & dstRobot, and (2) all jobs managed by
     *         this with id <= jobId that were formerly assigned to srcRobot are now assigned to dstRobot;
     *         and false otherwise
     * N.B., this method returns false only if either (1) this does not have either srcRobot or dstRobot, or (2) there
     * exists some job managed by this with id <= jobId that was formerly assigned to srcRobot and is currently not
     * assigned to dstRobot.
     */
    public boolean moveJobs(Robot srcRobot, Robot dstRobot, int jobId) {
        if (srcRobot == null || dstRobot == null) { return false; }
        if (srcRobot.isNull() || dstRobot.isNull()) { return false; }
        if (jobId < 1) { return false; }
        if (!this.robotToJobs.containsKey(srcRobot) || !this.robotToJobs.containsKey(dstRobot)) { return false; }
        Set<Integer> jobsMov = new HashSet<>();

        for (Integer job : this.robotToJobs.get(srcRobot)) {
            if (job <= jobId) {
                jobsMov.add(job);
            } else {
                break;
            }
        }
        for (Integer job : jobsMov) {
            this.robotToJobs.get(dstRobot).add(job);
            this.robotToJobs.get(srcRobot).remove(job);
            this.unassignedJobs.remove(job);
        }

        checkRep();

        return true;
    }

    /**
     * Moves all jobs managed by this JobManager from a specified robot to another specified robot when this has both
     * specified robots
     *
     * @param srcRobot
     * @param dstRobot
     * @return true if both of the following hold true: (1) this has srcRobot & dstRobot, and (2) all jobs managed by
     *         this that were formerly assigned to srcRobot are now assigned to dstRobot; and false otherwise
     * N.B., this method returns false only if either (1) this does not have either srcRobot or dstRobot, or (2) there
     * exists some job managed by this that was formerly assigned to srcRobot and is currently not assigned to dstRobot.
     */
    public boolean moveJobs(Robot srcRobot, Robot dstRobot) {
        if (srcRobot == null || dstRobot == null) { return false; }
        if (srcRobot.isNull() || dstRobot.isNull()) { return false; }
        if (!this.robotToJobs.containsKey(srcRobot) || !this.robotToJobs.containsKey(dstRobot)) { return false; }
        TreeSet<Integer> srcJobs = this.robotToJobs.get(srcRobot);

        this.robotToJobs.get(dstRobot).addAll(srcJobs);
        srcJobs.clear();
        for (Integer job : this.robotToJobs.get(dstRobot)) {
            this.unassignedJobs.remove(job);
        }
        checkRep();
        return true;
    }

    /**
     * Gets the highest priority job assigned to a specified robot in this JobManager with id <= a specified id when
     * this has the specified robot
     *
     * @param robot
     * @param jobId
     * @return the id of the highest priority job assigned to robot with id <= jobId; or a null job id of 0 if either no
     *         such job or robot exists in this
     */
    public int getHighestPriorityJob(Robot robot, int jobId) {
        if (robot == null || robot.isNull()) { return 0; }
        if (jobId < 1) { return 0; }
        if (!this.robotToJobs.containsKey(robot)) { return 0; }
        if (this.robotToJobs.get(robot).isEmpty()) { return 0; }
        checkRep();
        return this.robotToJobs.get(robot).floor(jobId);
    }

    /**
     * Compares the specified object with this JobManager for equality
     *
     * @param o
     * @ return true iff (1) this and o have the same pool of Robots as determined by id, (2) each Robot in this is
     *          assigned the same jobs as its equivalent Robot in o, and (3) this and o have the same unassigned jobs
     */
    @Override
    public boolean equals(Object o) {
        if (o == null | !(o instanceof JobManager)) { return false; }
        JobManager other = (JobManager) o;
        if (this.n != other.n) { return false; }
        if (!this.unassignedJobs.equals(other.unassignedJobs)) { return false; }
        return this.robotToJobs.equals(other.robotToJobs);
    }

    /**
     * Returns the hashcode for this JobManager
     *
     * @return the hashcode value which is
     *         min{
     *         (highest priority job in this) + [sum of (Robot.id * the highest priority job assigned to Robot), for every Robot in this],
     *         Integer.MAX_VALUE
     *         }
     */
    @Override
    public int hashCode() {
        long hash = this.n;
        for (Robot robot : this.robotToJobs.keySet()) {
            if (this.robotToJobs.get(robot).isEmpty()) { continue; }
            long term = robot.id * this.robotToJobs.get(robot).last();
            hash += term;
            if (hash > Integer.MAX_VALUE) { return Integer.MAX_VALUE; }
        }
        return (int)hash;
    }

    /**
     * Checks the representation invariant of this
     *
     * @throws AssertionError if the representation invariant is violated
     */
    public void checkRep() {

        if (n < 1) {
            throw new AssertionError("At least 1 job");
        }


        if (unassignedJobs.isEmpty() && robotToJobs.isEmpty()) {
            throw new AssertionError("At least one unassigned or assigned job");
        }


        for (Integer jobId : unassignedJobs) {
            if (jobId < 1 || jobId > n) {
                throw new AssertionError("Unassigned job " + jobId + " is invalid");
            }
        }


        for (Map.Entry<Robot, TreeSet<Integer>> entry : robotToJobs.entrySet()) {
            Robot robot = entry.getKey();
            TreeSet<Integer> jobs = entry.getValue();

            if (robot == null || robot.isNull()) {
                throw new AssertionError("Robot cannot be null");
            }

            Integer preJob = null;
            for (Integer jobId : jobs) {
                if (jobId < 1 || jobId > n) {
                    throw new AssertionError( robot.id + " has an invalid id " + jobId);
                }

                if (preJob != null && preJob >= jobId) {
                    throw new AssertionError("Jobs ID for Robot " + robot.id + " are not in increasing order");
                }
                preJob = jobId;
            }
        }


        for (Map.Entry<Robot, TreeSet<Integer>> entry : robotToJobs.entrySet()) {
            TreeSet<Integer> jobs = entry.getValue();
            for (Integer jobId : jobs) {
                if (unassignedJobs.contains(jobId)) {
                    throw new AssertionError("Job " + jobId + " is both assigned and unassigned");
                }
            }
        }


        Set<Integer> AssignedJobs = new HashSet<>();
        for (Map.Entry<Robot, TreeSet<Integer>> entry : robotToJobs.entrySet()) {
            for (Integer jobId : entry.getValue()) {
                if (!AssignedJobs.add(jobId)) {
                    throw new AssertionError("Job " + jobId + " is assigned to many robots");
                }
            }
        }
    }
}