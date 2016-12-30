import org.ejml.simple.SimpleMatrix;
import java.util.*;

/**
 * Created by maheshshanbhag on 04/12/16.
 */
public class main {
    public static void main (String[] args) {
        // Computes the page rank.
        computePageRanks();

        // Executes the HITS algorithm on the WebGraph.
        HITS();

        // Computes the Topic based ranks.
        computeTopicBasedRanks();
    }

    private static double[] computePageRanks() {
        // Set up the out links for the given documents
        double pd1 = 1./8, pd2 = 1./8, pd3 = 1./8, pd4 = 1./8, pd5 = 1./8, pd6 = 1./8 ,pd7 = 1./8 ,pd8 = 1./8;
        int cd1 = 4;
        int cd2 = 2;
        int cd3 = 3;
        int cd4 = 3;
        int cd5 = 4;
        int cd6 = 3;
        int cd7 = 3;
        int cd8 = 3;

        // This is to check the convergence of the Page Rank and stop the iterations.
        // Initially set to high value.
        double diff_pd1 = 999;
        double diff_pd2 = 999;
        double diff_pd3 = 999;
        double diff_pd4 = 999;
        double diff_pd5 = 999;
        double diff_pd6 = 999;
        double diff_pd7 = 999;
        double diff_pd8 = 999;

        // The damping factor of the algorithm.
        double DAMPING_FACTOR = 0.85;

        // Set up the equations for Page Ranking and iterate.
        int idx = 0;
        double min_diff = 0.0001;
        while (!((diff_pd1 < min_diff) && (diff_pd2 < min_diff) && (diff_pd3 < min_diff) && (diff_pd4 < min_diff)
                && (diff_pd5 < min_diff) && (diff_pd6 < min_diff) && (diff_pd7 < min_diff) && (diff_pd8 < min_diff))) {

            double old_pd1 = pd1;
            double old_pd2 = pd2;
            double old_pd3 = pd3;
            double old_pd4 = pd4;
            double old_pd5 = pd5;
            double old_pd6 = pd6;
            double old_pd7 = pd7;
            double old_pd8 = pd8;

            pd1 = (1 - DAMPING_FACTOR) + ((DAMPING_FACTOR) * (old_pd7/cd7 + old_pd8/cd8 + old_pd3/cd3));
            pd2 = (1 - DAMPING_FACTOR) + ((DAMPING_FACTOR) * (old_pd1/cd1 + old_pd8/cd8 + old_pd6/cd6 + old_pd5/cd5));
            pd3 = (1 - DAMPING_FACTOR) + ((DAMPING_FACTOR) * (old_pd1/cd1 + old_pd7/cd7 + old_pd6/cd6));
            pd4 = (1 - DAMPING_FACTOR) + ((DAMPING_FACTOR) * (old_pd2/cd2 + old_pd5/cd5));
            pd5 = (1 - DAMPING_FACTOR) + ((DAMPING_FACTOR) * (old_pd1/cd1 + old_pd3/cd3 + old_pd4/cd4 + old_pd6/cd6 + old_pd7/cd7));
            pd6 = (1 - DAMPING_FACTOR) + ((DAMPING_FACTOR) * (old_pd4/cd4 + old_pd5/cd5));
            pd7 = (1 - DAMPING_FACTOR) + ((DAMPING_FACTOR) * (old_pd1/cd1 + old_pd2/cd2 + old_pd4/cd4 + old_pd8/cd8));
            pd8 = (1 - DAMPING_FACTOR) + ((DAMPING_FACTOR) * (old_pd3/cd3 + old_pd5/cd5));

            diff_pd1 = Math.abs(old_pd1 - pd1);
            diff_pd2 = Math.abs(old_pd2 - pd2);
            diff_pd3 = Math.abs(old_pd3 - pd3);
            diff_pd4 = Math.abs(old_pd4 - pd4);
            diff_pd5 = Math.abs(old_pd5 - pd5);
            diff_pd6 = Math.abs(old_pd6 - pd6);
            diff_pd7 = Math.abs(old_pd7 - pd7);
            diff_pd8 = Math.abs(old_pd8 - pd8);

            idx++;
        }

        double[] pageRank = new double[8];
        pageRank[0] = pd1;
        pageRank[1] = pd2;
        pageRank[2] = pd3;
        pageRank[3] = pd4;
        pageRank[4] = pd5;
        pageRank[5] = pd6;
        pageRank[6] = pd7;
        pageRank[7] = pd8;

        return pageRank;

    }

    private static void HITS() {
        // Set up the HUBS and Authority links.
        HashMap<String, ArrayList<String>> document_out_links = new HashMap<>();
        ArrayList <String> array_d1 = new ArrayList<>(Arrays.asList("d2", "d3", "d5", "d7"));
        ArrayList <String> array_d2 = new ArrayList<>(Arrays.asList("d4", "d7"));
        ArrayList <String> array_d3 = new ArrayList<>(Arrays.asList("d1", "d5", "d8"));
        ArrayList <String> array_d4 = new ArrayList<>(Arrays.asList("d5", "d6", "d7"));
        ArrayList <String> array_d5 = new ArrayList<>(Arrays.asList("d2", "d4", "d6", "d8"));
        ArrayList <String> array_d6 = new ArrayList<>(Arrays.asList("d2", "d3", "d5"));
        ArrayList <String> array_d7 = new ArrayList<>(Arrays.asList("d1", "d3", "d5"));
        ArrayList <String> array_d8 = new ArrayList<>(Arrays.asList("d1", "d2", "d7"));
        createHub(document_out_links, "d1" ,array_d1);
        createHub(document_out_links, "d2" ,array_d2);
        createHub(document_out_links, "d3" ,array_d3);
        createHub(document_out_links, "d4" ,array_d4);
        createHub(document_out_links, "d5" ,array_d5);
        createHub(document_out_links, "d6" ,array_d6);
        createHub(document_out_links, "d7" ,array_d7);
        createHub(document_out_links, "d8" ,array_d8);

        // Authorities.
        HashMap<String, ArrayList<String>> document_in_links = new HashMap<>();
        ArrayList <String> aarray_d1 = new ArrayList<>(Arrays.asList("d7", "d8", "d3"));
        ArrayList <String> aarray_d2 = new ArrayList<>(Arrays.asList("d1", "d8", "d6", "d5"));
        ArrayList <String> aarray_d3 = new ArrayList<>(Arrays.asList("d1", "d7", "d6"));
        ArrayList <String> aarray_d4 = new ArrayList<>(Arrays.asList("d2", "d5"));
        ArrayList <String> aarray_d5 = new ArrayList<>(Arrays.asList("d1", "d3", "d4", "d6", "d7"));
        ArrayList <String> aarray_d6 = new ArrayList<>(Arrays.asList("d4", "d5"));
        ArrayList <String> aarray_d7 = new ArrayList<>(Arrays.asList("d1", "d2", "d4", "d8"));
        ArrayList <String> aarray_d8 = new ArrayList<>(Arrays.asList("d3", "d5"));
        createAuthorities(document_in_links, "d1" ,aarray_d1);
        createAuthorities(document_in_links, "d2" ,aarray_d2);
        createAuthorities(document_in_links, "d3" ,aarray_d3);
        createAuthorities(document_in_links, "d4" ,aarray_d4);
        createAuthorities(document_in_links, "d5" ,aarray_d5);
        createAuthorities(document_in_links, "d6" ,aarray_d6);
        createAuthorities(document_in_links, "d7" ,aarray_d7);
        createAuthorities(document_in_links, "d8" ,aarray_d8);

        HashMap<String, Double>hubScore = new HashMap<>();
        HashMap<String, Double>authorityScore = new HashMap<>();

        HashMap<String, Double>oldHubScore = new HashMap<>();
        HashMap<String, Double>oldAuthorityScore = new HashMap<>();

        // Considering D5 as the root set element.
        ArrayList<String> rootSet = new ArrayList<>();
        rootSet.add("d5");
        int root_set_size_before =  rootSet.size();
        int root_set_size_after =  0;
        while (root_set_size_after != root_set_size_before) {
            root_set_size_before = rootSet.size();

            // Get all the documents that are pointed the elements in the root set.
            ArrayList<String> rootSetStrings = new ArrayList<>(rootSet);
            for (String str : rootSetStrings) {
                ArrayList<String> out_links = document_out_links.get(str);

                for (String link: out_links) {
                    if (!rootSet.contains(link)) {
                        rootSet.add(link);
                    }
                }

            }

            // Get all the documents that are pointing the elements in the root set.
            for (String document: document_in_links.keySet()) {
                if (!rootSet.contains(document)) {
                    ArrayList<String> out_links = document_out_links.get(document);
                    for (String link : out_links) {
                        if (rootSet.contains(link)) {
                            rootSet.add(document);
                            break;
                        }
                    }
                }
            }

            root_set_size_after = rootSet.size();
        }


        ArrayList<String> baseSet = new ArrayList<>(rootSet);
        for (String document: baseSet) {
            hubScore.put(document, 1.);
            oldHubScore.put(document, 99.);
            authorityScore.put(document, 1.);
            oldAuthorityScore.put(document, 99.);
        }
        int documentbase_set_document_count = 8;
        int iteration_count = 0;

        while (!checkConvergence(oldHubScore, hubScore, oldAuthorityScore, authorityScore)) {
            // Update the hub scores.
            double hub_max_value = -1;
            for (int idx = 0; idx < documentbase_set_document_count; idx++) {
                String update_document = baseSet.get(idx);
                ArrayList<String> document_pointing_to = document_out_links.get(update_document);

                // Set the old hub score.
                oldHubScore.put(update_document, hubScore.get(update_document));

                double score = 0;
                for (String document : document_pointing_to) {
                    score += authorityScore.get(document);

                    hubScore.put(update_document, (score));
                }

                hub_max_value = Math.max(hub_max_value, score);
            }

            // Update the authority scores.
            double authorities_max_value = 0.;
            for (int idx = 0; idx < documentbase_set_document_count; idx++) {
                String update_document = baseSet.get(idx);

                // Set the old authority score.
                oldAuthorityScore.put(update_document, authorityScore.get(update_document));

                ArrayList<String> document_pointed_by = document_in_links.get(update_document);

                double score = 0;
                for (String document : document_pointed_by) {
                    score += hubScore.get(document);

                    authorityScore.put(update_document, (score));
                }

                authorities_max_value = Math.max(authorities_max_value, score);
            }

            // Normalize the values of the hub and authority scores.
            for (String document: hubScore.keySet()) {
                double normalized_value = hubScore.get(document)/ hub_max_value;
                hubScore.put(document, normalized_value);
            }

            for (String document: authorityScore.keySet()) {
                double normalized_value = authorityScore.get(document)/ authorities_max_value;
                authorityScore.put(document, normalized_value);
            }


            System.out.print((iteration_count+1) + ",");
            ArrayList<String> authority_keys = new ArrayList<>(authorityScore.keySet());
            Collections.sort(authority_keys);
            for (String document : authority_keys){
                System.out.print(authorityScore.get(document) + ",");
            }

            System.out.println();


            iteration_count++;
        }
    }

    private static boolean checkConvergence(HashMap<String, Double>oldHubScore, HashMap<String, Double>hubScore, HashMap<String, Double>oldAuthorityScore, HashMap<String, Double>authorityScore) {

        double convergence_to_decimal_point = 0.00001;
        int merge_count = 0;
        for (String document: oldHubScore.keySet()) {
            double difference = Math.abs(oldHubScore.get(document) - hubScore.get(document));
            if (difference < convergence_to_decimal_point) {
                merge_count++;
            }
        }

        for (String document: oldAuthorityScore.keySet()) {
            double difference = Math.abs(oldAuthorityScore.get(document) - authorityScore.get(document));
            if (difference < convergence_to_decimal_point) {
                merge_count++;
            }
        }

        if (merge_count == (oldAuthorityScore.keySet().size() * 2)) {
            return true;
        }

        return false;
    }

    private static void createHub(HashMap<String, ArrayList<String>> hubs, String hub, ArrayList <String> authorities) {
        hubs.put(hub, authorities);
    }

    private static void createAuthorities(HashMap<String, ArrayList<String>> auth_hash_map, String authority, ArrayList <String> authorities) {
        auth_hash_map.put(authority, authorities);
    }

    public static void computeTopicBasedRanks(){
        HashMap<String, ArrayList<String>> outLinks = addOutLinks();
        HashMap<String, ArrayList<String>> categories = initializeCategories();

        double[] stocastic_matrix_values = new double[(8 * 8)];
        for (int j = 0; j < 8; j++) {
            // Get the list of out links.
            ArrayList outlinkList = outLinks.get(Integer.toString(j+1));
            for (int i =0; i <8; i++) {
                if (outlinkList.contains(Integer.toString(i+1))) {
                    double outlink_count = outLinks.get(Integer.toString(i+1)).size();
                    stocastic_matrix_values[(j * 8) + i] = 1/outlink_count;
                }
                else {
                    stocastic_matrix_values[(j * 8) + i] = 0;
                }
            }
        }

        // USING EJML MATRIX LIBRARY FOR JAVA - MAKES LIFE EASIER FOR MATRIX COMPUTATIONS.
        // Get the Stocastic matrix.
        SimpleMatrix matrix = new SimpleMatrix(8,8,true,stocastic_matrix_values);
        double DampingFactor = 0.85;

        // Scale the stocastic matrix with the damping factor.
        matrix =  matrix.scale((1-DampingFactor));

        // Get page rank as a matrix.
        double[] pagerank = computePageRanks();
        SimpleMatrix page_rank_matrix = new SimpleMatrix(8,1,true, pagerank);

        // Compute the product of the page rank matrix and the scaled stocastic matrix.
        SimpleMatrix productMatrix = matrix.mult(page_rank_matrix);

        // Create appropriate vector for the Category.
        double[] biased_vector_category_a = new double[8];
        double[] biased_vector_category_b = new double[8];
        double[] biased_vector_category_c = new double[8];

        // Initialize the category vectors.
        // Using the ODPL Bias scheme where if the URL is present in the cluster of the category then the
        // corresponding position in the matrix will receive a value of 1/(number of URLs in the cluster) else 0.
        ArrayList<String>category_car = categories.get("cars");
        ArrayList<String>category_softwares = categories.get("software");
        ArrayList<String>category_allergies = categories.get("allergies");


        for (int i = 0; i < 8; i++) {
            if (category_car.contains(Integer.toString(i+1))) {
                biased_vector_category_a[i] = 1.0/category_car.size();
            }
            else {
                biased_vector_category_a[i] = 0;
            }
        }

        for (int i = 0; i < 8; i++) {
            if (category_allergies.contains(Integer.toString(i+1))) {
                biased_vector_category_b[i] = 1.0/category_softwares.size();
            }
            else {
                biased_vector_category_b[i] = 0;
            }
        }

        for (int i = 0; i < 8; i++) {
            if (category_allergies.contains(Integer.toString(i+1))) {
                biased_vector_category_c[i] =1.0/category_allergies.size();
            }
            else {
                biased_vector_category_c[i] = 0;
            }
        }

        // Get the matrix representation of the category vectors.
        SimpleMatrix category_a_matrix = new SimpleMatrix(8, 1, true, biased_vector_category_a);
        SimpleMatrix category_b_matrix = new SimpleMatrix(8, 1, true, biased_vector_category_b);
        SimpleMatrix category_c_matrix = new SimpleMatrix(8, 1, true, biased_vector_category_c);

        // Scale and add the category vectors with the Ranking vectors.
        category_a_matrix = category_a_matrix.scale(DampingFactor);
        category_b_matrix = category_b_matrix.scale(DampingFactor);
        category_c_matrix = category_c_matrix.scale(DampingFactor);


        // These are the new rankings of the URLs in the cluster (Specific categories).
        category_a_matrix = category_a_matrix.plus(productMatrix);
        category_b_matrix = category_b_matrix.plus(productMatrix);
        category_c_matrix = category_c_matrix.plus(productMatrix);

        // Printing the matrix.
        category_a_matrix.print();
        category_b_matrix.print();
        category_c_matrix.print();
    }

    static HashMap<String, ArrayList<String>> addOutLinks()
    {
        // Returns all the outlinks of the WebGraph as a HashMap.
        HashMap<String, ArrayList<String>> outLinks = new HashMap<>();
        ArrayList<String> linkList = new ArrayList<String>();
        linkList.add("2");
        linkList.add("3");
        linkList.add("5");
        linkList.add("7");
        outLinks.put("1",linkList);
        linkList = new ArrayList<String>();
        linkList.add("4");
        linkList.add("7");
        outLinks.put("2", linkList);
        linkList = new ArrayList<String>();
        linkList.add("1");
        linkList.add("5");
        linkList.add("8");
        outLinks.put("3", linkList);
        linkList = new ArrayList<String>();
        linkList.add("5");
        linkList.add("6");
        linkList.add("7");
        outLinks.put("4", linkList);
        linkList = new ArrayList<String>();
        linkList.add("2");
        linkList.add("4");
        linkList.add("6");
        linkList.add("8");
        outLinks.put("5", linkList);
        linkList = new ArrayList<String>();
        linkList.add("2");
        linkList.add("3");
        linkList.add("5");
        outLinks.put("6", linkList);
        linkList = new ArrayList<String>();
        linkList.add("1");
        linkList.add("3");
        linkList.add("5");
        outLinks.put("7", linkList);
        linkList = new ArrayList<String>();
        linkList.add("1");
        linkList.add("2");
        linkList.add("7");
        outLinks.put("8", linkList);

        return outLinks;
    }

    static HashMap<String, ArrayList<String>> initializeCategories()
    {
        // Returns the clustered categories as a Hashmap.
        HashMap<String, ArrayList<String>> categories = new HashMap<>();
        ArrayList<String> categDocs = new ArrayList<String>();
        categDocs.add("1");
        categDocs.add("5");
        categories.put("cars", categDocs);
        categDocs = new ArrayList<String>();
        categDocs.add("3");
        categDocs.add("4");
        categDocs.add("6");
        categDocs.add("8");
        categories.put("software", categDocs);
        categDocs = new ArrayList<String>();
        categDocs.add("2");
        categDocs.add("7");
        categories.put("allergies", categDocs);

        return categories;
    }
}
