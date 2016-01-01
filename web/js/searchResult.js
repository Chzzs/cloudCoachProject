var text = '[{"exercices":"[]","description":"super trainning","title":"training for big guys"},{"exercices":"[]","description":"shit trainning","title":"training for Nerd"}]';


console.log("coucou");

var jsonobj =JSON.parse(text);

console.log(jsonobj[0].description);
