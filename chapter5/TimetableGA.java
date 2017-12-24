package chapter5;

public class TimetableGA {

    public static void main(String[] args) {

        Timetable timetable = initializeTimetable();

        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.9, 2, 5);

        Population population = ga.initPopulation(timetable);

        ga.evalPopulation(population, timetable);

        int generation =1;

        while (ga.isTerminationConditionMet(generation, 1000) ==false 
            && ga.isTerminationConditionMet(population) == false) {
            System.out.println("G" + generation + "Best fitness: " + population.getFittest(0).getFitness());
            population = ga.crossoverPopulation(population);


            ga.evalPopulation(population, timetable);

            generation++;
        }

        timetable.createClasses(population.getFittest(0));
        System.out.println();
        System.out.println("Solution found in " + generation + " generations");
        System.out.println("Final solution fitness: " + population.getFittest(0).getFitness());
        System.out.println();
        System.out.println("Clashes: " + timetable.calcClashes());
        System.out.println();
        Class classes[] = timetable.getClasses();
        int classIndex=1;
        for (Class bestClass: classes) {
            System.out.println("Class " + classIndex + ":");
            System.out.println("Module: " + timetable.getModule(bestClass.getModuleId()).getModuleName());
            System.out.println("Group: " + timetable.getGroup(bestClass.getGroupId()));
            System.out.println("Room: " + timetable.getRoom(bestClass.getRoomId()).getRoomNumber());
            System.out.println("Professor: " + timetable.getProfessor(bestClass.getProfessorId()).getProfessorName());
            System.out.println("Time: " + timetable.getTimeslot(bestClass.getTimeslotId()).getTimeslot());
            System.out.println("------");
            classIndex++;
        }
        
    }

    //initializing method
    private static Timetable initializeTimetable() {

        Timetable timetable = new Timetable();

        timetable.addRoom(1, "A1", 15);
        timetable.addRoom(2, "B1", 30);
        timetable.addRoom(4, "D1", 20);
        timetable.addRoom(5, "F1", 25);

        timetable.addTimeslot(1, "Mon 9:00 - 11:00");
        timetable.addTimeslot(2, "Mon 11:00 - 13:00");
        timetable.addTimeslot(3, "Mon 13:00 - 15:00");
        timetable.addTimeslot(4, "Tue 9:00 - 11:00");
        timetable.addTimeslot(5, "Tue 11:00 - 13:00");
        timetable.addTimeslot(6, "Tue 13:00 - 15:00");
        timetable.addTimeslot(7, "Wed 9:00 - 11:00");
        timetable.addTimeslot(8, "Wed 11:00 - 13:00");
        timetable.addTimeslot(9, "Wed 13:00 - 15:00");
        timetable.addTimeslot(10, "Thu 9:00 - 11:00");
        timetable.addTimeslot(11, "Thu 11:00 - 13:00");
        timetable.addTimeslot(13, "Thu 13:00 - 15:00");
        timetable.addTimeslot(14, "Fri 9:00 - 11:00");
        timetable.addTimeslot(15, "Fri 11:00 - 13:00");
        timetable.addTimeslot(16, "Fri 13:00 - 15:00");

        timetable.addProfessor(1, "Dr A");
        timetable.addProfessor(2, "Dr B");
        timetable.addProfessor(3, "Dr C");
        timetable.addProfessor(4, "Dr D");

        timetable.addModule(1, "cs1", "Computer Science", new int[] {1,2});
        timetable.addModule(2, "en1", "English", new int[] {1,3});
        timetable.addModule(3, "ma1", "Maths", new int[] {1,2});
        timetable.addModule(4, "ph1", "Physics", new int[] {3,4});
        timetable.addModule(5, "hi1", "History", new int[] {4});
        timetable.addModule(6, "dr1", "Drama", new int[] {1,4});

        timetable.addGroup(1, 10, new int[] {1,3,4});
        timetable.addGroup(2, 30, new int[] {2,3,5,6});
        timetable.addGroup(3, 18, new int[] {3,4,5});
        timetable.addGroup(4, 25, new int[] {1,4});
        timetable.addGroup(5, 20, new int[] {2,3,5});
        timetable.addGroup(6, 22, new int[] {1,4,5});
        timetable.addGroup(7, 16, new int[]{1,3});
        timetable.addGroup(8, 18, new int[]{2,6});
        timetable.addGroup(9, 24, new int[]{1,6});
        timetable.addGroup(10, 25, new int[]{3,4});
        
        return timetable;
    }

}