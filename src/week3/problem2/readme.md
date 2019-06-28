# Group 1 Week 3 Assignment Summary

As always, our code has a debug toggle at the beginning, you can use that to control the amount of printout.

## Question 1:

1. This part of the problem, we are asked to write a very basic multi-dimensional unbounded array.

2. Our approach is once we have generated one unbouneded array, we will just stuff another dimension into it. This is fully implemented for the randomwriter class.

3. We have written a small tester program to verify our Unbounded array.

## Question 2:

### Approach:

This question is broken down into 6 parts, as listed below:

1. Main Funtion that handles parameter as well as basic running of the code

2. The Sequential Runner function that runs all below function in order

3. The read from source file function

4. The generate probabilistics function

5. The generate sentence function

6. the write to source file function

#### sequentialRunner()

This function is responsible for calling all the function in order so that for debugging purposes, we can easiliy stop a block of function

#### readFromSource()

This function is responsible for taking care of file read. It also guards the program with failed file name. This function will write to private String that stores the source file text

#### generateProbablisitics()

For this problem, there is really two approaches, we can parse through the whole program, or we can parse on demand. However, I think for the purpose of better runtime performance, I parsed all the source text before starting generation. This gives me advantage for future expandability.

The source text is segmented per requirement. Excessive space as well as special characters are handled by english convention, as space, period, hyphan, and comma all have their role in a sentence. Excessive special characters are handled by simply being ignored. Each segmented piece is stored in a 1D expandable array type we wrote. This will act as our dictionary, and will tell us where to look for the probability distribution table.

Once the segmentation is done, we simply look over one character and simply store it in an expandable 2D array type we created. We didn't acutally calculate the detailed distribution for each potential character since if we have an array full of potentials, when random number generators generate a random number within the arraybound, it will represent the potential distribution. Calculating the distribution takes time and is not as efficient as just simply shuffle these potential in to a bin and pick one eventually.

#### generate Sentence()

for random generator, we first call function initialSeedGenerator. The initialSeedGenerator will basically look for the specification and pick a seed for us to start generating the sentence. Once seed is picked, we lookup the seed inside the lookup table, and then we get the table index. We then pass the index to the 2D array of probabilites. We then tell randomGenerator to generate a number and use thath number to pick up a potential character.

In the case of seed runs out, we ask the initial Seed Generator again and it will give us another seed, and we repeat he above steps until fullfill the length requirement or seed run out again

#### writeTOSource()

This function is responsible writing the result to a file. It again, gaurds file operation as well as extracted so in the future we can potentially re-use it.

#### getOPTIME()

This function allows us to get the operation time per request.

### Experiment Result:

#### Operation Time:

As far as we observe, with Java Array List implementation and our implementation, there are really not a lot of code runtime difference. This is potentially because we are not looking at such a big scale, as well as our seed is not fixed so time will vary from seed to seed.

#### Results:

**Overall, we observed that with a larger segmentation size, we can achieve more human readable results.**

##### INPUT1:

A man on a tired gray horse reined in where a dim cattle-trail dropped into a gulch, and looked behind him. Nothing was in sight. He half closed his eyes and searched the horizon. No, there was nothing--just the same old sand and sage-brush, hills, more sand and sage-brush, and then to the west and north the spur of the Rockies, whose jagged peaks were white with a fresh fall of snow. The wind was chill. He shivered, and looked to the eastward. For the last few hours he had felt snow in the air, and now he could see it in the dim, gray mist--still far off, but creeping toward him.

##### Output1:

**Following result is generated with Size2**
|Run # | Result |
|--- |--- |
|Run 1 |lf ng ose and and and nd the kies, sh ng- seas rush, raire alf then st le- izon. earchistwas gge- opp |
|Run 2 |ing ockies, izon. towast th nd eing- ire off, oseeping ow ng of theresh, st far lched ll the the hit |
|Run 3 |nd ow he gulchit wing gge- war Rockiest nd ed, same rch, jage- ge- ast- astir, and nto He it st ere |

**Following result is generated with Size4**
|Run # | Result |
|--- |--- |
|Run 1 |there ward spur orizon. thing could rail shivered, creeping ivered, fall hose ined behind othing- horiz|
|Run 2| were ills, same ills, chills, hours here oked oked ping spur here then ockies, eeping rail oked reepi|
|Run 3| sand stward. orizon. ight. oward thing closed with eping ours izon. here there oked eping thing- opped|

##### Input 2

the thrown rock then flew that way, through the thin feather pillow.

##### Output 2

**Following result is generated with Size2**
| Run # | Result |
| ----- | ------------------------------------------------------------------------------------------------------- |
|Run 1|throw. ough rough ough gh ew rough ock hrock flew ock athrock hrow. rough at through hrough ther llo|
|Run 2| ew the en he er lown throck then ough hrough lew llown llow. hin the the through then ugh pillow. ck |
|Run 3|through llow. ther ay, ck wn en eat feathe er ugh in her way, rown ther eathe ew hat gh lew en throu|

**Following result is generated with Size4**

| Run # | Result                                                                                                  |
| ----- | ------------------------------------------------------------------------------------------------------- |
| Run 1 | pillow. flew feather flew through through rough feather rock flew thrown rough llow. llow. illow. then  |
| Run 2 | then illow. illow. ather then llow. thin hrough rock ther rock llow. through thin hrough rown hrown     |
| Run 3 | that flew then eather eather pillow. hrough rown through pillow. pillow. flew rown thrown feather hroug |

#### Input 3:
Mr. Bennet was so odd a mixture of quick parts, sarcastic humour, reserve, and caprice, that the experience of three-and-twenty years had been insufficient to make his wife understand his character. _Her_ mind was less difficult to develop. She was a woman of mean understanding, little information, and uncertain temper. When she was discontented, she fancied herself nervous. The business of her life was to get her daughters married; its solace was visiting and news.

#### Output 3:
**Following result is generated with Size2**
| Run # | Result |
| ----- | ------------------------------------------------------------------------------------------------------- |
|Run 1| nce, ess ittless Whe get unce stainess vour, and marstancer. Ther. elf little rsers been rmat quicul|
|Run 2| uick re ess lt astand rcastance life us. Bencience, tionty fick sherve, ng cult ndevelf odd Ben suff|
|Run 3| ervousition, informake usittless cied fic nd- ttle ing, hation, actempervousition, he he ndifficult|

**Following result is generated with Size6**
| Run # | Result |
| ----- | ------------------------------------------------------------------------------------------------------- |
|Run 1| derstanding, nsufficient business ontented, ughters rstanding, erstanding, little ancied sarcastic ervous. |
|Run 2|ughters fficult twenty scontented, usiness herself information, character. daughters ixture icient arried; |
|Run 3| nformation, sufficient evelop. solace fancied scontented, develop. erience ixture information, siting |