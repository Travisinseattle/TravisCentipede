package group7.tcss450.uw.edu.centipedeandroid.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import group7.tcss450.uw.edu.centipedeandroid.R;
/**
 * Centipede class that works as a linked list of nodes which are centipede bodies.
 * Class can add new nodes to the list and update the locations of the nodes. Also handles the
 * bitmap images for the nodes.
 *
 * Created by addison on 1/24/2017.
 */

class Centipede {


    /** Starting position for centipede*/
    private final static int CENTIPEDE_Y = -100;

    /** Height divisor for block size */
    private final static int HEIGHT_DIVISOR = 15;

    /** Starting x position for centipede */
    private final static int STARTING_X = 2;

    /** Size of each square in the boards grid*/
    private int mBlockSize;

    /** Bitmap of the centipede head */
    private Bitmap mCentipedeHead;

    /** Size of Y axis of screen */
    private int mScreenY;

    /** X axis size of screen */
    private int mScreenX;

    /** Size of the centipede */
    private int mSize;

    /** Head node of the centipede */
    private CentipedeBody mHead;

    // debugging
//    private int b;

    /**
     * Centipede constructor that takes in a centipedebody
     * and makes it the new head of a new centipede.
     * @param head a centipedebody.
     */
    public Centipede(CentipedeBody head) {
        mHead = head;
        CentipedeBody temp = mHead;
        while (temp != null) {
            mSize++;
            temp = temp.getNext();
        }
    }

    /**
     * Constructor for the centipede class.
     *
     * @param context  The context of the parent constructing the ship.
     * @param screenX  The X dimensions of the parent activity.
     * @param screenY  The Y dimensions of the parent activity.
     * @param block    The size of a block on the screen.
     */
    Centipede(Context context, int screenX, int screenY, int block) {
        mScreenY = screenY;
        mScreenX = screenX;
        mSize = 0;
        mHead = null;
        mBlockSize = block;
        boolean isStart = true;
        Bitmap bodyBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.centipede);
        mCentipedeHead =  BitmapFactory.decodeResource(context.getResources(), R.drawable.centipedehead2);
        if (isStart) {
            createCentipede(bodyBitmap);
            isStart = false;
        }
    }

    /**
     * Method that creates the centipede bodies.
     * @param bitmap the bitmap image used for the bodies.
     */
    private void createCentipede(Bitmap bitmap) {
        int k = 0;
        for (int i = 0; i < 15; i++) {
            if (i == 0) {
                addNode(mCentipedeHead,mScreenX/STARTING_X, -(mScreenY/HEIGHT_DIVISOR)-k, mScreenX, mScreenY);
            } else {
                addNode(bitmap,mScreenX/STARTING_X, -(mScreenY/HEIGHT_DIVISOR)-k, mScreenX, mScreenY);
            }
            k+=mHead.getHeight();
        }
    }

    // testing and debugging does nothing
//    public int getB() {
//        return b;
//    }

    /**
     * Method that adds a node to the list and checks if the
     * list is null and assigns the first node as the head
     * , otherwise it links the other nodes together that are being
     * added.
     *
     * @param bitmap is the bitmap picture of the node.
     * @param xPos is the x position of the node.
     * @param yPos is the y position of the node.
     */
    private void addNode(Bitmap bitmap, int xPos, int yPos, int screenX, int screenY) {
        CentipedeBody newNode = new CentipedeBody(bitmap, xPos, yPos, screenX, screenY, mBlockSize);
        if (mHead == null) {
            mHead = newNode;
        } else {
            CentipedeBody temp = mHead;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(newNode);
        }
        mSize++;
    }

    /**
     * Getter method that returns the head node of the centipede.
     *
     * @return a node that is the head of the centipede.
     */
    CentipedeBody getHead() {
        return mHead;
    }

    /**
     *  Getter that returns the size of the centipede.
     * @return an int of the size of the centipede.
     */
    int getSize() {
        return this.mSize;
    }

    /**
     *  Setter that sets the size of the centipede.
     */
    void setSize() {
        if (this.mSize >= 1) {
            this.mSize--;
        }
    }

    /**
     * Method that calls all of the centipede nodes update method that updates where
     * each node is on the screen.
     */
    void update() {
        CentipedeBody temp = mHead;
        while (temp != null) {
            if (temp.getVisible()) {
                temp.update();
            }
            temp = temp.getNext();
        }
    }
}
