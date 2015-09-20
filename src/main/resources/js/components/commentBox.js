var fetchCommentsUrl= 'http://localhost:8080/api/comments';
var data = [
  {author: "Pete Hunt", text: "This is one comment"},
  {author: "Jordan Walke", text: "This is *another* hashtag"}
];
var CommentBox = React.createClass({

  createComment: function(comment){
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      method: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(comment),
      success: function(data) {

      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });

  },
  getInitialState: function() {
    return {data: []};
  },
  reload: function(){
    $.ajax({
          url: this.props.url,
          dataType: 'json',
          cache: false,
          success: function(data) {
            this.setState({data: data.content});
          }.bind(this),
          error: function(xhr, status, err) {
            console.error(this.props.url, status, err.toString());
          }.bind(this)
        });
  },
  componentDidMount: function() {
      this.reload;
      setInterval(this.reload, this.props.pollInterval);
    },
  render: function() {
    return (
    <div className="commentBox">
      <h1>Comments</h1>
       <CommentList data={this.state.data}/>
       <CommentForm onCommentSubmit={this.createComment} />
    </div>
    );
  }
});

var CommentList = React.createClass({
  render: function() {
    var comments = this.props.data.map(function(comment){
       return (<Comment author={comment.author}>{comment.text}</Comment>);
    });
    return (
      <div className="commentList">
        {comments}
      </div>
    );
  }
});

var CommentForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var author = React.findDOMNode(this.refs.author).value.trim();
    var text = React.findDOMNode(this.refs.text).value.trim();
    if (!text || !author) {
      return;
    }
    this.props.onCommentSubmit({author: author, text: text});
    React.findDOMNode(this.refs.author).value = '';
    React.findDOMNode(this.refs.text).value = '';
    return;
  },
  render: function() {
    return (
      <div className="commentForm" onSubmit={this.handleSubmit}>
        <form className="commentForm">
          <input type="text" placeholder="Your name" ref="author"/>
          <input type="text" placeholder="Say something..." ref="text"/>
          <input type="submit" value="Post" />
        </form>
      </div>
    );
  }
});
var Comment = React.createClass({
  render: function() {
    var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
    return (

      <div className="comment">
        <h2 className="commentAuthor">
          {this.props.author}
        </h2>
        <span dangerouslySetInnerHTML={{__html: rawMarkup}} />
      </div>
    );
  }
});


React.render(
  <CommentBox url={fetchCommentsUrl} pollInterval={2000}/>,
  document.getElementById('content')
);