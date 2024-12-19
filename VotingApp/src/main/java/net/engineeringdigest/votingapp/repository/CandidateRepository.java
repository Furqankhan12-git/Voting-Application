package net.engineeringdigest.votingapp.repository;




import net.engineeringdigest.votingapp.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {
}


