package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.QuestionDTO;
import com.iset.spring_integration.dto.TestDTO;
import com.iset.spring_integration.entities.Question;
import com.iset.spring_integration.entities.Test;
import com.iset.spring_integration.repositories.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final TestRepository testRepository;

    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }
    @GetMapping
    public List<TestDTO> getAllTests() {
        return testRepository.findAllWithQuestions().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TestDTO convertToDTO(Test test) {
        TestDTO dto = new TestDTO();
        dto.setId(test.getId());
        dto.setTitle(test.getTitle());
        dto.setLanguage(test.getLanguage());
        dto.setDescription(test.getDescription());
        dto.setDuration(test.getDuration());
        dto.setDifficultyLevels(test.getDifficultyLevels());

        List<QuestionDTO> questionDTOs = test.getQuestions().stream().map(this::convertQuestionToDTO).toList();
        dto.setQuestions(questionDTOs);

        return dto;
    }

    private QuestionDTO convertQuestionToDTO(Question q) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(q.getId());
        dto.setContenu(q.getContenu());
        dto.setDifficulty(q.getDifficulty());
        dto.setBonneReponse(q.getBonneReponse());
        dto.setTopic(q.getTopic());
        dto.setReponseA(q.getReponses().getA());
        dto.setReponseB(q.getReponses().getB());
        dto.setReponseC(q.getReponses().getC());
        dto.setReponseD(q.getReponses().getD());
        dto.setExplanation(q.getExplanation());
        dto.setTimeLimit(q.getTimeLimit());
        return dto;
    }
}
