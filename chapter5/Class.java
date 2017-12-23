package chapter5;

public class Class {
    private final int classId;
    private final int groupId; 
    private final int moduleId;
    private int professorId;
    private int timeslotId; 
    private int roomId;
    
    public Class(int classId, int groupId, int moduleId) {
        this.classId = classId;
        this.groupId = groupId;
        this.moduleId = moduleId;
    }

    public void addProfessor(int professorId) {
        this.professorId = professorId;
    }

    public void addTimeslot(int timeslotId) {
        this.timeslotId = timeslotId;
    }

    public void setRoom(int roomId) {
        this.roomId = roomId;
    }

    public int getClassId() {
        return this.classId;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public int getModuleId() {
        return this.moduleId;
    }

    public int getProfessorId() {
        return this.professorId;
    }

    public int getTimeslotId() {
        return this.timeslotId;
    }

    public int getRoomId() {
        return this.roomId;
    }

}