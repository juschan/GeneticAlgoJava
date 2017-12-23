package chapter3;
import java.util.ArrayList;

public class Robot {

    private enum Direction {NORTH, EAST, SOUTH, WEST};

    private int xPosition;
    private int yPosition;
    private Direction heading;
    int maxMoves;
    int moves;
    private int sensorVal;
    private final int sensorActions[];
    private Maze maze;
    private ArrayList<int[]> route;

    public Robot(int[] sensorActions, Maze maze, int maxMoves) {
        this.sensorActions = this.calcSensorActions(sensorActions);
        this.maze=maze;
        int startPos[] = this.maze.getStartPosition();
        this.xPosition = startPos[0];
        this.yPosition = startPos[1];
        this.sensorVal = -1;
        this.heading = Direction.EAST;
        this.maxMoves = maxMoves;
        this.route = new ArrayList<int[]>();
        this.route.add(startPos);
    }

    public void run() {
        while(true) {
            this.moves++;

            //Test if no more moves
            if( this.getNextAction()==0 || //robot stops moving
                this.maze.getPositionValue(this.xPosition,this.yPosition)==4 || //reach end goal  
                this.moves > this.maxMoves) //reach max number of moves
                return;
            
            //otherwise, make the next move
            this.makeNextAction();
        }
    }

    private int[] calcSensorActions(int[] sensorActionsStr){
        int numActions = (int) sensorActionsStr.length /2;
        int sensorActions[] = new int[numActions];

        for(int sensorValue =0; sensorValue < numActions; sensorValue++) {
            int sensorAction=0;
            if(sensorActionsStr[sensorValue*2]==1) {
                sensorAction +=2;
            }
            if(sensorActionsStr[sensorValue*2]+1==1) {
                sensorAction +=1;
            }

            sensorActions[sensorValue] = sensorAction;
        }
        return sensorActions;
    }

    public void makeNextAction() {
        if (this.getNextAction() ==1) {
            int currentX = this.xPosition;
            int currentY = this.yPosition;

            if(Direction.NORTH == this.heading) {
                this.yPosition +=-1;
                if(this.yPosition<0) {
                    this.yPosition=0;
                }
            }
            else if(Direction.EAST == this.heading) {
                this.xPosition +=-1;
                if(this.xPosition>this.maze.getMaxX()) {
                    this.xPosition=this.maze.getMaxX();
                }
            }
            else if(Direction.SOUTH == this.heading) {
                this.yPosition +=-1;
                if(this.yPosition>this.maze.getMaxY()) {
                    this.yPosition=this.maze.getMaxY();
                }
            }
            else if(Direction.WEST == this.heading) {
                this.xPosition +=-1;
                if(this.xPosition<0) {
                    this.xPosition=0;
                }
            }

            //walls - can't move
            if(this.maze.isWall(this.xPosition, this.yPosition) == true) {
                this.xPosition=currentX;
                this.yPosition=currentY;
            }
            else {
                if(currentX !=this.xPosition || currentY !=this.yPosition) {
                    this.route.add(this.getPosition());
                }
            }
        }
        else if(this.getNextAction()==2) {
            if(Direction.NORTH == this.heading) {
                this.heading=Direction.EAST;
            }
            else if(Direction.EAST == this.heading) {
                this.heading=Direction.SOUTH;
            }
            else if(Direction.SOUTH == this.heading) {
                this.heading=Direction.WEST;
            }
            else if(Direction.WEST == this.heading) {
                this.heading=Direction.NORTH;
            }
        }
        else if(this.getNextAction()==3) {
            if(Direction.NORTH == this.heading) {
                this.heading=Direction.WEST;
            }
            else if(Direction.EAST == this.heading) {
                this.heading=Direction.NORTH;
            }
            else if(Direction.SOUTH == this.heading) {
                this.heading=Direction.EAST;
            }
            else if(Direction.WEST == this.heading) {
                this.heading=Direction.SOUTH;
            }
        }
        this.sensorVal=-1;
    }

    public int getNextAction() {
        return this.sensorActions[this.getSensorValue()];
    }

    public int getSensorValue() {
        if(this.sensorVal >-1) {
            return this.sensorVal;
        }

        boolean frontSensor, frontLeftSensor, frontRightSensor, leftSensor, rightSensor, backSensor;
        frontSensor = frontLeftSensor = frontRightSensor = leftSensor = rightSensor = backSensor = false;

        if(this.getHeading() == Direction.NORTH) {
            frontSensor=this.maze.isWall(this.xPosition, this.yPosition-1);
            frontLeftSensor = this.maze.isWall(this.xPosition-1, this.yPosition-1);
            frontRightSensor=this.maze.isWall(this.xPosition+1, this.yPosition-1);
            leftSensor = this.maze.isWall(this.xPosition-1, this.yPosition);
            rightSensor=this.maze.isWall(this.xPosition+1, this.yPosition);
            backSensor = this.maze.isWall(this.xPosition, this.yPosition);
        }
        else if(this.getHeading() == Direction.EAST) {
            frontSensor=this.maze.isWall(this.xPosition+1, this.yPosition);
            frontLeftSensor = this.maze.isWall(this.xPosition+1, this.yPosition-1);
            frontRightSensor=this.maze.isWall(this.xPosition+1, this.yPosition+1);
            leftSensor = this.maze.isWall(this.xPosition, this.yPosition-1);
            rightSensor=this.maze.isWall(this.xPosition, this.yPosition+1);
            backSensor = this.maze.isWall(this.xPosition-1, this.yPosition);
        }
        else if(this.getHeading() == Direction.SOUTH) {
            frontSensor=this.maze.isWall(this.xPosition+1, this.yPosition+1);
            frontLeftSensor = this.maze.isWall(this.xPosition+1, this.yPosition+1);
            frontRightSensor=this.maze.isWall(this.xPosition-1, this.yPosition+1);
            leftSensor = this.maze.isWall(this.xPosition+1, this.yPosition);
            rightSensor=this.maze.isWall(this.xPosition-1, this.yPosition);
            backSensor = this.maze.isWall(this.xPosition, this.yPosition-1);
        }
        else {
            frontSensor=this.maze.isWall(this.xPosition-1, this.yPosition);
            frontLeftSensor = this.maze.isWall(this.xPosition-1, this.yPosition-1);
            frontRightSensor=this.maze.isWall(this.xPosition-1, this.yPosition-1);
            leftSensor = this.maze.isWall(this.xPosition, this.yPosition+1);
            rightSensor=this.maze.isWall(this.xPosition, this.yPosition-1);
            backSensor = this.maze.isWall(this.xPosition+1, this.yPosition);
        }
        
        //calc sensor value
        int sensorVal=0;
        if(frontSensor==true) {
            sensorVal+=1;
        }
        if(frontLeftSensor==true) {
            sensorVal+=2;
        }
        if(frontRightSensor==true) {
            sensorVal+=4;
        }
        if(leftSensor==true) {
            sensorVal+=8;
        }
        if(rightSensor==true) {
            sensorVal+=16;
        }
        if(backSensor==true) {
            sensorVal+=32;
        }
        this.sensorVal=sensorVal;
        return sensorVal;
        
    }

    public int[] getPosition() {
        return new int[]{this.xPosition, this.yPosition};
    }

    private Direction getHeading() {
        return this.heading;
    }

    public ArrayList<int[]> getRoute() {
        return this.route;
    }

    public String printRoute() {
        String route = "";
        for(Object routeStep : this.route) {
            int step[] = (int[]) routeStep;
            route += "{" + step[0] + "," + step[1] + "}";
        }
        return route;
    }

}