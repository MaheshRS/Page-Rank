# Page links
The Page Rank (Original and Topic based) are calculated based on the following document links:
D1 ->  D2, D1 ->  D3, D1 ->  D5, D1 -> D7 
D2 ->  D4, D2 ->  D7
D3 ->  D1, D3 ->  D5, D3 ->  D8
D4 ->  D5, D4 ->  D6, D4 ->  D7 
D5 ->  D2, D5 ->  D4, D5 ->  D6, D5 ->  D8
D6 ->  D2, D6 ->  D3, D6 ->  D5
D7 ->  D1, D7 ->  D3, D7 ->  D5
D8 ->  D1, D8 ->  D2, D8 ->  D7

# Page Rank
The page rank solution calculates the pages rank according to the formulae:
PR(A)= (1−d)+ d (PR(T1)/C(T1) + … +PR(Tn)/C(Tn))

Where,
- C(A) is defined as the number of links going out of page A
- A has pages T1...Tn which point to it (i.e., are citations).
- d is a damping factor and is set to 0.85.

# HITS
The following formulae is used to calculate the Hub score and Authority score:
- Hub Score: h(x)← ∑x a(y)A
- Authority Score:a(x)← ∑x h(y)  

The scaling factor used for calculation the Hub score and the Authority is as follows:
- Hub score scaling factor: 1/(Max of all the Hub scores)
- Authority score scaling factor:  1/(Max of all the Authority scores)

The program stops the iterating for computing scores when the value of all Hub and Authority scores converges and does not change unto 4 decimal places.

# Topic based Page Rank
For computing the topic specific page ranks the below formulae is used:
R a n k = (1 - α )M × R a n k + α p'
where, 
- M is the stochastic matrix.
- Rank is the Page Ranks of the documents.
- p' is the biased  vector and N x 1 matrix. 
- α is damping factor.

Here for the construction of the p' is as follows:
- The above ranking equation use the ODP based Biasing.
- In ODP based Biasing while constructing the p', for a category the p' is a vector made of components equal to the number of Webpages.
- Each position in the vector corresponds to the to a Web page as listed above.  If the cluster includes in the Web page then in the corresponding position in the p' vector the value is 1/(Number of web pages in the cluster) else it is 0.