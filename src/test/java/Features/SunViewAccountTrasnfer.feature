Feature: AccountTransfer testing

Scenario Outline: perform a successful single Transfer
	Given Login to SunView as "<row_Index>" customer
	When user provides provides account transfer data as in "<row_Index>"
	And Submits the transfer
	Then That Transfer should be successful
Examples:
    |row_Index|
    |1|
    |2|