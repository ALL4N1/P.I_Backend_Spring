package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Question;
import com.iset.spring_integration.repositories.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Ajouter une question
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Modifier une question
    public Question updateQuestion(Long id, Question newQuestion) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question non trouvée"));

        question.setContenu(newQuestion.getContenu());
        question.setBonneReponse(newQuestion.getBonneReponse());
        question.setReponses(newQuestion.getReponses());
        return questionRepository.save(question);
    }

    // Supprimer une question
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new EntityNotFoundException("Question non trouvée");
        }
        questionRepository.deleteById(id);
    }
}
