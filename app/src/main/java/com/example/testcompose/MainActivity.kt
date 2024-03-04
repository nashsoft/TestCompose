package com.example.testcompose
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


data class Contact(val name: String, val phoneNumber: String, val iconResId: Int)

@Composable
fun ContactList(contacts: List<Contact>, onContactClick: (Contact) -> Unit) {
    LazyColumn {
        items(contacts) { contact ->
            ContactItem(contact = contact, onContactClick)
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onContactClick: (Contact) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onContactClick(contact) }
    ) {
        Icon(
            painter = painterResource(id = contact.iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(shape = MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = contact.name)
            Text(text = contact.phoneNumber)
        }
    }
}

@Composable
fun ContactSearchBar(
    modifier: Modifier = Modifier,
    onSearchTextChange: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearchTextChange(it)
        },
        placeholder = { Text("Поиск контактов") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Sentences
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}

@Composable
fun ContactScreen() {
    var filteredContacts by remember { mutableStateOf(emptyList<Contact>()) }
    val contacts = remember {
        listOf(
            Contact("Любимая", "+7 (702) 911-01-03", R.drawable.ic_heart),
            Contact("Владимир Ветров", "+7 (701) 700-12-34", R.drawable.ic_contact_default),
            Contact("Петруха Колесников", "+7 (701) 705-12-15", R.drawable.ic_login),
            Contact("Габит Муканов", "+7 (707) 005-92-75", R.drawable.ic_google),
            Contact("Заур Хачатурян", "+7 (702) 555-00-11", R.drawable.img_login),
            Contact("Андрейка", "+7 (747) 123-49-91", R.drawable.img_motocycle_01),
            Contact("БамбоЛейла", "+7 (700) 121-12-21", R.drawable.ic_facebook),
            /*Contact("Мама дом", "+7 (7162) 55-11-33", R.drawable.rounded_red_bg),
            Contact("Мама сот.", "+7 (777) 755-11-33", R.drawable.ic_launcher_background),
            Contact("Мама раб.", "+7 (7162) 91-30-19", R.drawable.ic_operator),
            Contact("Батя сот.", "+7 (702) 349-57-99", R.drawable.rounded_white_button_bg),
        */)
    }

    var searchText by remember { mutableStateOf("") }

    Column {
        ContactSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onSearchTextChange = {
                searchText = it
                filteredContacts = contacts.filter { contact ->
                    contact.name.contains(searchText, ignoreCase = true) ||
                    contact.phoneNumber.contains(searchText, ignoreCase = true)
                }
            }
        )

        ContactList(contacts = if (searchText.isNotEmpty()) filteredContacts else contacts) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactScreenPreview() {
    ContactScreen()
}