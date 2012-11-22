package project;

/*
 *    MauiModelBuilder.java
 *    Copyright (C) 2009 Olena Medelyan
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
import gnu.trove.TIntHashSet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

import stemmers.FrenchStemmer;
import stemmers.PorterStemmer;
import stemmers.Stemmer;
import stopwords.Stopwords;
import stopwords.StopwordsEnglish;
import stopwords.StopwordsFrench;

import org.wikipedia.miner.model.Wikipedia;
import org.wikipedia.miner.util.ProgressNotifier;
import org.wikipedia.miner.util.text.CaseFolder;
import org.wikipedia.miner.util.text.TextProcessor;

/**
 * Demonstrates how to use Maui for three types of topic indexing  <br>
 * 1. Keyphrase extraction - extracting significant phrases from 
 *  the document, also suitable for automatic tagging. <br>
 * 2. Term assignment - indexing documents with terms
 * from a controlled vocabulary in SKOS or text format. <br> 
 * 3. Indexing with Wikipedia - indexing documents with 
 * terms from Wikipedia, also suitable for
 * keyphrase extraction and tagging, or any case where there is no con	trolled
 * vocabulary available, but consistency is required.
 * 
 * @author Olena Medelyan (olena@cs.waikato.ac.nz)
 * 
 */
public class Examples {

	private MauiTopicExtractor topicExtractor;
	private MauiModelBuilder modelBuilder;

	private Wikipedia wikipedia;

	private String server;
	private String database;
	private String dataDirectory;
	private boolean cache = false;
	
	public Examples (String server, String database, String dataDirectory, boolean cache) throws Exception  {
		this.server = server;
		this.database = database;
		this.dataDirectory = dataDirectory;
		this.cache = cache;
		loadWikipedia();
	}

	public Examples ()  {	}

	private void loadWikipedia() throws Exception {

		wikipedia = new Wikipedia(server, database, "root", null);

		TextProcessor textProcessor = new CaseFolder();

		File dataDir = new File(dataDirectory);
		
		if (cache) {
			ProgressNotifier progress = new ProgressNotifier(5);
			// cache tables that will be used extensively
			TIntHashSet validPageIds = wikipedia.getDatabase().getValidPageIds(
					dataDir, 2, progress);
			wikipedia.getDatabase().cachePages(dataDir, validPageIds,
					progress);
			wikipedia.getDatabase().cacheAnchors(dataDir, textProcessor,
					validPageIds, 2, progress);
			wikipedia.getDatabase().cacheInLinks(dataDir, validPageIds,
					progress);
			wikipedia.getDatabase().cacheGenerality(dataDir, validPageIds, progress);
		}
	}
	
	/**
	 * Sets general parameters: debugging printout, language specific options
	 * like stemmer, stopwords.
	 * @throws Exception 
	 */
	private void setGeneralOptions()  {
	
		
		modelBuilder.setDebug(true);
		modelBuilder.setWikipedia(wikipedia);
		
		/* language specific options
		Stemmer stemmer = new FrenchStemmer();
		Stopwords stopwords = new StopwordsFrench();
		String language = "fr";
		String encoding = "UTF-8";
		modelBuilder.setStemmer(stemmer);
		modelBuilder.setStopwords(stopwords);
		modelBuilder.setDocumentLanguage(language);
		modelBuilder.setEncoding(encoding);
		topicExtractor.setStemmer(stemmer);
		topicExtractor.setStopwords(stopwords);
		topicExtractor.setDocumentLanguage(language);
		*/
		
		/* specificity options
		modelBuilder.setMinPhraseLength(1);
		modelBuilder.setMaxPhraseLength(5);
		*/
	
		topicExtractor.setDebug(true);
		topicExtractor.setNumTopics(10); // how many topics to extract
		topicExtractor.setWikipedia(wikipedia);
	}

	/**
	 * Set which features to use
	 */
	private void setFeatures() {
		modelBuilder.setBasicFeatures(true);
		modelBuilder.setKeyphrasenessFeature(true);
		modelBuilder.setFrequencyFeatures(true);
		modelBuilder.setPositionsFeatures(true);
		modelBuilder.setLengthFeature(true);
		modelBuilder.setNodeDegreeFeature(true);
		modelBuilder.setBasicWikipediaFeatures(false);
		modelBuilder.setAllWikipediaFeatures(false);
	}

	/**
	 * Demonstrates how to perform automatic tagging. Also applicable to
	 * keyphrase extraction.
	 * 
	 * @throws Exception
	 */
	public LinkedList<String> automaticTagging(String abstractInput) throws Exception {
		topicExtractor = new MauiTopicExtractor();
		modelBuilder = new MauiModelBuilder();
		setGeneralOptions();
		setFeatures();
		
		// Directories with train & test data
		String trainDir = "src/data/automatic_tagging/train/";
		String testDir = "src/data/automatic_tagging/test/";

		// name of the file to save the model
		String modelName = "test.txt";

		// Settings for the model builder
		modelBuilder.setDirName(trainDir);
		modelBuilder.setModelName(modelName);
		
		// change to 1 for short documents
		modelBuilder.setMinNumOccur(2);

		// Run model builder
		HashSet<String> fileNames = modelBuilder.collectStems();
		
		// Settings for topic extractor
		topicExtractor.setDirName(testDir);
		topicExtractor.setModelName(modelName);
	
		// Run topic extractor
		topicExtractor.loadModel();
		fileNames = topicExtractor.collectStems();
		//topicExtractor.extractKeyphrases(fileNames);
                LinkedList<String> topicsList = new LinkedList();
                topicsList = topicExtractor.extractKeyphrases(abstractInput);
                takeADeepBreath(); // I am not familiar with threading, so I am cheating here
                return topicsList;
        }

        /**
         * Borrowed idea from programmershaven.com
         */
        public static void takeADeepBreath() {
            int n = 500;
            long t0, t1;
            t0 = System.currentTimeMillis();
            do {
                t1 = System.currentTimeMillis();
            } while (t1 - t0 < n);
        }
	

	

	/**
	 * Main method for running the three types of topic indexing. Comment out
	 * the required one.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
            
	}

}
