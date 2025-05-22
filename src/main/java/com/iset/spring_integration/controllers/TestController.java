package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.*;
import com.iset.spring_integration.entities.*;
import com.iset.spring_integration.repositories.TestRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final TestRepository testRepository;

    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    // Endpoint GET existant
    @GetMapping
    public List<TestDTO> getAllTests() {
        List<Test> tests = testRepository.findAll(Sort.by("id"));
        return tests.stream().map(this::convertToDTO).toList();
    }

    // Endpoint POST modifié
    @PostMapping
    public ResponseEntity<TestDTO> createTest(@RequestBody TestCreationDTO creationDTO) {
        Test newTest = convertToEntity(creationDTO);
        Test savedTest = testRepository.save(newTest);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedTest));
    }
    // TestController.java
    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getTestById(@PathVariable Long id) {
        return testRepository.findById(id)
                .map(test -> ResponseEntity.ok(convertToDTO(test)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé"));
    }

    // Endpoint PUT modifié
    @PutMapping("/{id}")
    public ResponseEntity<TestDTO> updateTest(
            @PathVariable Long id,
            @RequestBody TestCreationDTO updateDTO
    ) {
        Test existingTest = testRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé"));

        updateTestFromDTO(existingTest, updateDTO);
        Test updatedTest = testRepository.save(existingTest);
        return ResponseEntity.ok(convertToDTO(updatedTest));
    }

    // Endpoint DELETE inchangé
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé"));

        testRepository.delete(test);
        return ResponseEntity.noContent().build();
    }

    // Méthodes de conversion
    private TestDTO convertToDTO(Test test) {
        TestDTO dto = new TestDTO();
        dto.setId(test.getId());
        dto.setTitle(test.getTitle());
        dto.setLanguage(test.getLanguage());
        dto.setDescription(test.getDescription());
        dto.setDuration(test.getDuration());
        dto.setDifficultyLevels(test.getDifficultyLevels());

        List<QuestionDTO> questions = test.getQuestions().stream()
                .map(this::convertQuestionToDTO)
                .toList();
        dto.setQuestions(questions);

        if(test.getMentorDetails() != null) {
            dto.setMentorDetails(convertMentorDetailsToDTO(test.getMentorDetails()));
        }

        return dto;
    }

    private MentorDetailsDTO convertMentorDetailsToDTO(MentorDetails details) {
        MentorDetailsDTO dto = new MentorDetailsDTO();
        dto.setDescription(details.getDescription());
        dto.setRequirements(details.getRequirements());
        dto.setBenefits(details.getBenefits());
        return dto;
    }

    private QuestionDTO convertQuestionToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setContenu(question.getContenu());
        dto.setDifficulty(question.getDifficulty().name());
        dto.setBonneReponse(question.getBonneReponse());
        dto.setTopic(question.getTopic());
        dto.setReponseA(question.getReponses().getA());
        dto.setReponseB(question.getReponses().getB());
        dto.setReponseC(question.getReponses().getC());
        dto.setReponseD(question.getReponses().getD());
        dto.setExplanation(question.getExplanation());
        dto.setTimeLimit(question.getTimeLimit());
        return dto;
    }

    private Test convertToEntity(TestCreationDTO dto) {
        Test test = new Test();
        updateTestFromDTO(test, dto);
        return test;
    }

    private void updateTestFromDTO(Test test, TestCreationDTO dto) {
        test.setTitle(dto.getTitle());
        test.setLanguage(dto.getLanguage());
        test.setDescription(dto.getDescription());
        test.setDuration(dto.getDuration());
        test.setDifficultyLevels(dto.getDifficultyLevels());

        // Mise à jour des questions
        updateQuestions(test, dto.getQuestions());

        // Mise à jour des détails du mentor
        updateMentorDetails(test, dto.getMentorDetails());
    }

    private void updateQuestions(Test test, List<QuestionCreationDTO> questionDTOs) {
        test.getQuestions().clear();
        questionDTOs.forEach(qDto -> {
            Question question = new Question();
            question.setContenu(qDto.getContenu());
            question.setDifficulty(QuestionDifficulty.valueOf(qDto.getDifficulty()));
            question.setBonneReponse(qDto.getBonneReponse());
            question.setTopic(qDto.getTopic());
            question.setExplanation(qDto.getExplanation());
            question.setTimeLimit(qDto.getTimeLimit());

            QuestionResponses responses = new QuestionResponses();
            responses.setA(qDto.getReponseA());
            responses.setB(qDto.getReponseB());
            responses.setC(qDto.getReponseC());
            responses.setD(qDto.getReponseD());
            question.setReponses(responses);

            question.setTest(test);
            test.getQuestions().add(question);
        });
    }

    private void updateMentorDetails(Test test, MentorDetailsDTO detailsDTO) {
        if(detailsDTO != null) {
            MentorDetails details = test.getMentorDetails() != null ?
                    test.getMentorDetails() : new MentorDetails();

            details.setDescription(detailsDTO.getDescription());
            details.setRequirements(detailsDTO.getRequirements());
            details.setBenefits(detailsDTO.getBenefits());
            test.setMentorDetails(details);
        }
    }
}