function toggleFields(switchId, freqGroupId, freqInputId, instGroupId, instInputId) {
    const switchInput = document.getElementById(switchId);
    const isChecked = switchInput.checked;

    const freqGroup = document.getElementById(freqGroupId);
    const freqInput = document.getElementById(freqInputId);
    const instGroup = document.getElementById(instGroupId);
    const instInput = document.getElementById(instInputId);

    if (freqGroup && instGroup && freqInput && instInput) {
        freqGroup.style.display = isChecked ? "block" : "none";
        instGroup.style.display = isChecked ? "none" : "block";

        freqInput.value = isChecked ? "MONTHLY" : "";
        instInput.value = isChecked ? "" : "3";
    }
}

function updateDescriptionFromCategory(selectId, inputId) {
    const select = document.getElementById(selectId);
    const selectedOption = select.options[select.selectedIndex];
    const categoryName = selectedOption?.getAttribute("data-name");

    if (categoryName) {
        document.getElementById(inputId).value = categoryName;
    }
}