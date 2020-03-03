
import java.util.*;
import java.util.stream.Collectors;
// This code gets 90/100
//https://www.coursera.org/learn/cryptocurrency/programming/Bn6DI/consensus-from-trust/discussions/threads/VKiamAWOEee-uQ7R_UJsXg

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {
    private double p_graph;
    private double p_malicious;
    private double p_txDistribution;
    private int numRounds;

    private boolean[] followees;
    private Set<Transaction> pendingTransactions;
    private boolean[] maliciousList;

    // Constructor for CompliantNode
    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
        this.p_graph = p_graph;
        this.p_malicious = p_malicious;
        this.p_txDistribution = p_txDistribution;
        this.numRounds = numRounds;
    }

    public void setFollowees(boolean[] followees) {
        this.followees = followees;
        this.maliciousList = new boolean[followees.length];
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

    public Set<Transaction> sendToFollowers() {
        Set<Transaction> sendTransactions = new HashSet<>(pendingTransactions);
        pendingTransactions.clear();
        return sendTransactions;
    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
        Set<Integer>sendingNodes = candidates.stream().map(candidate -> candidate.sender).collect(Collectors.toSet());

        for (int i = 0; i < followees.length; i++) {
            if(followees[i] && !sendingNodes.contains(i)){
                maliciousList[i] = true;
            }
        }

        for (Candidate candidate: candidates) {
            if(!maliciousList[candidate.sender]){
                pendingTransactions.add(candidate.tx);
            }
        }
    }

}
