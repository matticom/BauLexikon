package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import exceptions.SpecialtyAlreadyExistsAsTechnicalTerm;
import exceptions.TechnicalTermAlreadyExistsAsSpecialty;
import model.Languages;
import model.Languages_;
import model.Specialty;
import model.TechnicalTerm;
import model.Term;
import model.Translations;
import model.Translations_;
import utilities.PersistenceUtil;

public class TermDAO {

	private EntityManager entitymanager;

	public TermDAO() {
	}

	public TermDAO(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}

	public EntityManager getEntitymanager() {
		return entitymanager;
	}

	public void setEntitymanager(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}

	public Specialty insertNewSpecialty(Specialty specialty) {

		entitymanager.persist(specialty);
		return specialty;
	}

	public Translations insertSpecialtyTranslation(Specialty specialty, Translations translation) {

		List<Translations> transList;
		transList = specialty.getTranslationList();
		transList.add(translation);
		entitymanager.persist(translation);
		return translation;
	}

	public TechnicalTerm insertNewTechnicalTerm(TechnicalTerm technicalTerm) {

		entitymanager.persist(technicalTerm);
		return technicalTerm;
	}

	public Translations insertTechnicalTermTranslation(TechnicalTerm technicalTerm, Translations translation) {

		List<Translations> transList;
		transList = technicalTerm.getTranslationList();
		transList.add(translation);

		entitymanager.persist(translation);
		return translation;
	}

	public void deleteSpecialty(Specialty specialty) {

		removeSpecialtyForeignKeyOutOfTechnicalTerms(specialty);
		entitymanager.remove(specialty);
	}

	public void deleteTechnicalTerm(TechnicalTerm technicalTerm) {

		removeTechnicalTermForeignKeyOutOfSpecialty(technicalTerm);
		entitymanager.remove(technicalTerm);
	}

	public void deleteTranslation(Translations translation) {

		Term term = translation.getTerm();
		term.getTranslationList().remove(translation);
		entitymanager.remove(translation);
	}

	public Translations updateTranslation(Translations translation, Translations newTranslation) {

		translation.setName(newTranslation.getName());
		translation.setNormalName(PersistenceUtil.convertSpecialChar(newTranslation.getName()));
		translation.setDescription(newTranslation.getDescription());
		return translation;
	}

	public Specialty selectSpecialtyById(int id) {

		Specialty specialty = entitymanager.find(Specialty.class, id);
		if (specialty == null) {
			throw new NoResultException("Specialty ID ist nicht vorhanden!");
		}
		return specialty;
	}

	public TechnicalTerm selectTechnicalTermById(int id) {

		TechnicalTerm technicalTerm = entitymanager.find(TechnicalTerm.class, id);
		if (technicalTerm == null) {
			throw new NoResultException("TechnicalTerm ID ist nicht vorhanden!");
		}
		return technicalTerm;
	}
	
	public Term selectTermById(int id) {

		Term term = entitymanager.find(Term.class, id);
		if (term == null) {
			throw new NoResultException("Term ID ist nicht vorhanden!");
		}
		return term;
	}

	public List<Specialty> selectAllSpecialties() {

		CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();
		CriteriaQuery<Specialty> criteriaQuery = criteriaBuilder.createQuery(Specialty.class);

		Root<Specialty> specialty = criteriaQuery.from(Specialty.class);
		criteriaQuery.select(specialty);

		List<Specialty> specialtyList = entitymanager.createQuery(criteriaQuery).getResultList();

		return specialtyList;
	}
	
	public List<TechnicalTerm> selectAllTechnicalTerms() {

		CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();
		CriteriaQuery<TechnicalTerm> criteriaQuery = criteriaBuilder.createQuery(TechnicalTerm.class);

		Root<TechnicalTerm> technicalTerm = criteriaQuery.from(TechnicalTerm.class);
		criteriaQuery.select(technicalTerm);

		List<TechnicalTerm> technicalTermList = entitymanager.createQuery(criteriaQuery).getResultList();

		return technicalTermList;
	}

	public Specialty selectSpecialtyByName(String normalName, Languages language) throws NoResultException {

		Specialty specialty;
		try {
			specialty = (Specialty) selectTermByName(normalName, language.getName());
		} catch (ClassCastException e) {
			throw new SpecialtyAlreadyExistsAsTechnicalTerm();
		}
		return specialty;
	}

	public TechnicalTerm selectTechnicalTermByName(String normalName, Languages language) throws NoResultException {

		TechnicalTerm technicalTerm;
		try {
			technicalTerm = (TechnicalTerm) selectTermByName(normalName, language.getName());
		} catch (ClassCastException e) {
			throw new TechnicalTermAlreadyExistsAsSpecialty();
		}
		return technicalTerm;
	}

	public List<Translations> selectAllTermTranslations(Term term) throws NoResultException {

		List<Translations> translationsList = term.getTranslationList();
		return translationsList;
	}

	private Term selectTermByName(String normalName, String lang) throws NoResultException {

		Translations translation = selectTranslation(normalName, lang);
		Term term = translation.getTerm();
		return term;
	}

	private Translations selectTranslation(String normalName, String lang) throws NoResultException {

		CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();
		CriteriaQuery<Translations> criteriaQuery = criteriaBuilder.createQuery(Translations.class);

		Root<Translations> translation = criteriaQuery.from(Translations.class);
		Join<Translations, Languages> langJoin = translation.join(Translations_.languages);

		Predicate selectLanguage = criteriaBuilder.like(criteriaBuilder.lower(langJoin.get(Languages_.name)), lang.toLowerCase());
		Predicate selectTermName = criteriaBuilder.like(criteriaBuilder.lower(translation.get(Translations_.normalName)), normalName.toLowerCase());
		Predicate whereFilter = criteriaBuilder.and(selectLanguage, selectTermName);
		criteriaQuery.select(translation).where(whereFilter);

		Translations translationResult = entitymanager.createQuery(criteriaQuery).getSingleResult();

		return translationResult;
	}
		
	public List<Translations> selectTranslations(String normalName) {

		CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();
		CriteriaQuery<Translations> criteriaQuery = criteriaBuilder.createQuery(Translations.class);

		Root<Translations> translation = criteriaQuery.from(Translations.class);
		Predicate whereFilter = criteriaBuilder.like(criteriaBuilder.lower(translation.get(Translations_.normalName)), normalName.toLowerCase());
		criteriaQuery.select(translation).where(whereFilter);

		return entitymanager.createQuery(criteriaQuery).getResultList();
	}
	
	private void removeSpecialtyForeignKeyOutOfTechnicalTerms(Specialty specialty) {

		for (TechnicalTerm techTerm : specialty.getTechnicalTermsList()) {
			techTerm.setSpecialty(null);
		}
		specialty.getTechnicalTermsList().clear();
	}

	private void removeTechnicalTermForeignKeyOutOfSpecialty(TechnicalTerm technicalTerm) {

		Specialty specialty = technicalTerm.getSpecialty();
		specialty.getTechnicalTermsList().remove(technicalTerm);
		technicalTerm.setSpecialty(null);
	}
}
