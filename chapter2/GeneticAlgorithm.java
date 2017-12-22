package chapter2;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    //constructor
    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    public Population initPopulation(int chromosomeLength) {
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }


    public double calcFitness(Individual individual) {
        //track number of correct genes
        int correctGenes = 0;

        for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
            if (individual.getGene(geneIndex)==1) {
                correctGenes+=1;
            }
        }

        //calculate fitness
        double fitness = (double) correctGenes/ individual.getChromosomeLength();

        //store fitness
        individual.setFitness(fitness);

        return fitness;
    }

    public void evalPopulation(Population population) {
        double populationFitness = 0;
        for (Individual individual : population.getIndividuals()) {
            populationFitness += calcFitness(individual);
        }

        population.setPopulationFitness(populationFitness);
    }
 
    
    public boolean isTerminationConditionMet(Population population) {
        for (Individual individual: population.getIndividuals()) {
            if(individual.getFitness() ==1) {
                return true;
            }
        }
        return false;
    }

    //run roulette wheel in reverse
    public Individual selectParent(Population population) {
        Individual individuals[] = population.getIndividuals();

        //spin roulette wheel
        double populationFitness = population.getPopulationFitness();
        double rouletteWheelPosition = Math.random() * populationFitness;

        //find parent
        double spinWheel =0;
        for (Individual individual : individuals) {
            spinWheel += individual.getFitness();
            if(spinWheel >= rouletteWheelPosition) {
                return individual;
            }
        }
        return individuals[population.size()-1];
    }

    public Population crossoverPopulation(Population population) {
        //create new population
        Population newPopulation = new Population(population.size());

        for (int populationIndex =0; populationIndex<population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);
        
            //apply crossover to this individual?
            if(this.crossoverRate > Math.random() && populationIndex > this.elitismCount) {
                //initialise offspring
                Individual offspring = new Individual(parent1.getChromosomeLength());

                //find second parent
                Individual parent2 = selectParent(population);

                //loop over genome
                for (int geneIndex=0; geneIndex<parent1.getChromosomeLength(); geneIndex++) {
                    //use half of each parent's genes. Uniform crossover
                    if(0.5 > Math.random()) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    }
                }

                //add offspring to new population
                newPopulation.setIndividual(populationIndex, offspring);
            
            } else {
                //add individual to new population without applying crossover
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;

    }


}