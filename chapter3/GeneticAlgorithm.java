package chapter3;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    protected int tournamentSize;

    //constructor
    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }

    public Population initPopulation(int chromosomeLength) {
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }

    public Population mutatePopulation(Population population) {
        Population newPopulation = new Population(this.populationSize);

        for (int populationIndex =0; populationIndex <population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);

            for (int geneIndex=0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                if(populationIndex>=this.elitismCount) {
                    if(this.mutationRate > Math.random()) {
                        int newGene=1;
                        if (individual.getGene(geneIndex)==1) {
                            newGene=0;
                        }
                        //mutate gene
                        individual.setGene(geneIndex,newGene);
                    }
                }
            }
            newPopulation.setIndividual(populationIndex, individual);
        }
        return newPopulation;
    }


}