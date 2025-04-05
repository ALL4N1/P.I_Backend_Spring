package com.iset.spring_integration.config ;

import com.github.javafaker.Faker;
import com.iset.spring_integration.entities.*;
import com.iset.spring_integration.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final TestRecrutementRepository testRecrutementRepository;
    private final QuestionRepository questionRepository;
    private CoursRepository coursRepository;
    private LanguageRepository languageRepository;
    private ChapitreRepository chapitreRepository;
    private final ReponseRepository reponseRepository;


    private final Faker faker = new Faker();
    private final Random random = new Random();

    public DataLoader(UtilisateurRepository utilisateurRepository,
                      CoursRepository coursRepository,
                      LanguageRepository languageRepository,
                      ChapitreRepository chapitreRepository,
                      TestRecrutementRepository testRecrutementRepository,
                      QuestionRepository questionRepository,
                      ReponseRepository reponseRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.coursRepository = coursRepository;
        this.languageRepository = languageRepository;
        this.chapitreRepository = chapitreRepository;
        this.testRecrutementRepository = testRecrutementRepository;
        this.questionRepository = questionRepository;
        this.reponseRepository = reponseRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        // Insérer 5 administrateurs
        for (int i = 0; i < 5; i++) {
            Administrateur admin = new Administrateur();
            admin.setNom(faker.name().fullName());
            admin.setEmail(faker.internet().emailAddress());
            admin.setMdp("admin123");
            utilisateurRepository.save(admin);
        }

        // Insérer 10 développeurs
        for (int i = 0; i < 10; i++) {
            Developpeur dev = new Developpeur();
            dev.setNom(faker.name().fullName());
            dev.setEmail(faker.internet().emailAddress());
            dev.setMdp("dev123");
            utilisateurRepository.save(dev);
        }
        // Liste des langages
        List<String> langues = Arrays.asList("Java", "Python", "JavaScript", "PHP", "Ruby");

        // Insérer 5 enseignants avec des cours
        for (int i = 0; i < 5; i++) {
            Enseignant enseignant = new Enseignant();
            enseignant.setNom(faker.name().fullName());
            enseignant.setEmail(faker.internet().emailAddress());
            enseignant.setMdp("ens123");
            utilisateurRepository.save(enseignant);

            // Ajouter des cours
            for (int j = 0; j < 2; j++) {
                Cours cour = new Cours();
                cour.setTitre(faker.book().title());
                cour.setContenu(faker.lorem().paragraph());
                cour.setEnseignant(enseignant);

                // Associer une langue aléatoire
                String langueChoisie = langues.get(random.nextInt(langues.size()));
                LanguageCours language = getOrCreateLanguage(langueChoisie);

                cour.setLanguage(language);

                coursRepository.save(cour);
            }
        }

        for (int i = 0; i < 3; i++) {
            TestRecrutement test = new TestRecrutement();
            test.setTitre("Test " + (i + 1));
            testRecrutementRepository.save(test);

            for (int j = 0; j < 4; j++) {
                Question question = new Question();
                question.setContenu("Q" + (j + 1) + ": " + faker.lorem().sentence());
                question.setTest(test);
                questionRepository.save(question);

                // Réponses
                for (int r = 0; r < 3; r++) {
                    Reponse reponse = new Reponse();
                    reponse.setTexte("Réponse " + (r + 1));
                    reponse.setCorrecte(r == 0); // Seule la première est correcte
                    reponse.setQuestion(question);
                    reponseRepository.save(reponse);
                }
            }
        }

        System.out.println("Données fictives insérées avec succès pour les utilisateurs/testRecrutment!");
        // Trouver un enseignant pour associer les cours
        Optional<Utilisateur> enseignantOpt = utilisateurRepository.findAll().stream()
                .filter(user -> user instanceof Enseignant)
                .findFirst();

        if (enseignantOpt.isEmpty()) {
            System.out.println("Aucun enseignant trouvé !");
            return;
        }

        Enseignant enseignant = (Enseignant) enseignantOpt.get();

        for (int j = 0; j < 5; j++) {
            Cours cours = new Cours();
            cours.setTitre(faker.book().title());
            cours.setContenu(faker.lorem().paragraph());
            cours.setEnseignant(enseignant);

            // Choisir une langue aléatoire
            String langueChoisie = langues.get(random.nextInt(langues.size()));

            // Vérifier si la langue existe déjà
            LanguageCours language = getOrCreateLanguage(langueChoisie);


            cours.setLanguage(language);

            Cours savedCours = coursRepository.save(cours);

            // Ajouter 3 chapitres (aléatoirement PDF, vidéo ou texte)
            for (int k = 0; k < 3; k++) {
                Chapitre chapitre = createRandomChapitre(savedCours);
                chapitreRepository.save(chapitre);
            }
        }
    }

    private Chapitre createRandomChapitre(Cours cours) {
        int type = random.nextInt(3);
        switch (type) {
            case 0 -> {
                ChapitrePdf pdf = new ChapitrePdf();
                pdf.setTitre(faker.book().title());
                pdf.setUrlPdf("http://example.com/pdf/" + faker.file().fileName());
                pdf.setCours(cours);
                return pdf;
            }
            case 1 -> {
                ChapitreVideo video = new ChapitreVideo();
                video.setTitre("Chapitre Vidéo: " + faker.book().title());
                video.setUrlVideo("http://example.com/video/" + faker.internet().url());
                video.setCours(cours);
                return video;
            }
            default -> {
                ChapitreTexte texte = new ChapitreTexte();
                texte.setTitre("Chapitre Texte: " + faker.book().title());
                texte.setContenu(faker.lorem().paragraph(5));
                texte.setCours(cours);
                return texte;
            }
        }

    }
    private LanguageCours getOrCreateLanguage(String nom) {
        Optional<LanguageCours> languageOpt = languageRepository.findByNom(nom);  // Cette méthode retourne un Optional

        if (languageOpt.isEmpty()) {  // Si l'Optional est vide, on crée une nouvelle langue
            LanguageCours newLang = new LanguageCours();
            newLang.setNom(nom);
            return languageRepository.save(newLang);
        } else {
            return languageOpt.get();  // Si une langue est trouvée, on la retourne
        }
    }



}
