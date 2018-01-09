Execute the gradle run task to see output in console
(./gradlew run in linux terminal)
See src folder for source code

Answer for challenge #1
{
  "valid_menus": [
    {
      "root_id": 2,
      "children": [4, 5, 6, 8]
    },
    {
      "root_id": 9,
      "children": [10, 11, 12, 13, 14]
    }
  ],
  "invalid_menus": [
    {
      "root_id": 1,
      "children": [1, 3, 7, 15]
    }
  ]
}

Answer for challenge #2
{
  "valid_menus": [
    {
      "root_id": 2,
      "children": [8, 9, 10, 11]
    },
    {
      "root_id": 12,
      "children": [13, 14, 15, 16, 17, 21]
    }
  ],
  "invalid_menus": [
    {
      "root_id": 1,
      "children": [1, 3, 4, 5, 6, 7, 18, 19, 20]
    }
  ]
}
