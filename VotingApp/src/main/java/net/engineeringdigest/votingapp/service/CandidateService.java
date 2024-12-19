package net.engineeringdigest.votingapp.service;


import net.engineeringdigest.votingapp.model.Candidate;
import net.engineeringdigest.votingapp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Candidate findById(String id) {
        return candidateRepository.findById(id).orElse(null);
    }

    public void save(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    @PostConstruct
    public void init() {
        // default candidates
        if (candidateRepository.count() == 0) {
            Candidate candidate1 = new Candidate();
            candidate1.setName("Candidate 1");
            candidate1.setVoteCount(0);
            candidateRepository.save(candidate1);

            Candidate candidate2 = new Candidate();
            candidate2.setName("Candidate 2");
            candidate2.setVoteCount(0);
            candidateRepository.save(candidate2);

            Candidate candidate3 = new Candidate();
            candidate3.setName("Candidate 3");
            candidate3.setVoteCount(0);
            candidateRepository.save(candidate3);

            Candidate candidate4 = new Candidate();
            candidate4.setName("Candidate 4");
            candidate4.setVoteCount(0);
            candidateRepository.save(candidate4);
        }
    }
}

